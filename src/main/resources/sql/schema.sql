CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE cryptocurrencies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallets (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    fund_balance NUMERIC(18, 2) DEFAULT 0 CHECK (fund_balance >= 0)
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    wallet_id INT NOT NULL REFERENCES wallets(id) ON DELETE CASCADE,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'BUY', 'SELL')),
    amount NUMERIC(18, 2) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des Crypto Buy/Sell Details
CREATE TABLE crypto_movements (
    id SERIAL PRIMARY KEY,
    transaction_id INT NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
    cryptocurrency_id INT NOT NULL REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
    quantity NUMERIC(18, 8) NOT NULL CHECK (quantity > 0)
);

-- Table Historique des Prix avec Open, High, Low, Close
CREATE TABLE price_history (
    id SERIAL PRIMARY KEY,
    cryptocurrency_id INT NOT NULL REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
    open NUMERIC(18, 8) NOT NULL CHECK (open >= 0),
    high NUMERIC(18, 8) NOT NULL CHECK (high >= open),
    low NUMERIC(18, 8) NOT NULL CHECK (low <= open AND low >= 0),
    close NUMERIC(18, 8) NOT NULL CHECK (close >= low AND close <= high),
    change NUMERIC(18, 8) NOT NULL,
    record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO cryptocurrencies (name, symbol)
VALUES
    ('Bitcoin', 'BTC'),
    ('Ethereum', 'ETH'),
    ('Binance Coin', 'BNB'),
    ('Cardano', 'ADA'),
    ('Solana', 'SOL'),
    ('Ripple', 'XRP'),
    ('Dogecoin', 'DOGE'),
    ('Polkadot', 'DOT'),
    ('Litecoin', 'LTC'),
    ('Chainlink', 'LINK');

INSERT INTO price_history (cryptocurrency_id, open, high, low, close, change, record_date)
VALUES
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'BTC'), 45000.12345678, 46000.12345678, 44000.12345678, 45500.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'ETH'), 3000.12345678, 3100.12345678, 2900.12345678, 3050.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'BNB'), 400.12345678, 410.12345678, 390.12345678, 405.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'ADA'), 1.12345678, 1.20000000, 1.00000000, 1.15000000, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'SOL'), 120.12345678, 125.12345678, 115.12345678, 122.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'XRP'), 0.50000000, 0.55000000, 0.48000000, 0.53000000, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'DOGE'), 0.08000000, 0.09000000, 0.07000000, 0.08500000, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'DOT'), 25.12345678, 27.12345678, 24.12345678, 26.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'LTC'), 150.12345678, 155.12345678, 145.12345678, 152.12345678, 0, CURRENT_TIMESTAMP),
    ((SELECT id FROM cryptocurrencies WHERE symbol = 'LINK'), 30.12345678, 32.12345678, 29.12345678, 31.12345678, 0, CURRENT_TIMESTAMP);

SELECT * FROM price_history WHERE cryptocurrency_id = 1 ORDER BY record_date DESC LIMIT 1;


DROP TABLE price_history;
DROP TABLE cryptocurrencies;

-- ================================================
-- VIEWS
-- ================================================

-- View of Wallet Balances
CREATE OR REPLACE VIEW wallet_balances_view AS
SELECT
    u.name AS users,
    w.fund_balance AS fund_balance,
    COALESCE(SUM(cm.quantity * ph.close), 0) AS crypto_value
FROM users u
         JOIN wallets w ON u.id = w.user_id
         LEFT JOIN crypto_movements cm ON w.id = cm.transaction_id
         LEFT JOIN cryptocurrencies c ON cm.cryptocurrency_id = c.id
         LEFT JOIN price_history ph ON ph.cryptocurrency_id = c.id
    AND ph.record_date = (
        SELECT MAX(record_date)
        FROM price_history
        WHERE cryptocurrency_id = c.id
    )
GROUP BY u.name, w.fund_balance;


-- View of a User's Transactions
CREATE VIEW user_transactions_view AS
SELECT
    u.name AS users,
    t.transaction_type,
    t.amount,
    t.transaction_date,
    c.name AS cryptocurrency,
    cm.quantity
FROM users u
         JOIN wallets w ON u.id = w.user_id
         JOIN transactions t ON w.id = t.wallet_id
         LEFT JOIN crypto_movements cm ON t.id = cm.transaction_id
         LEFT JOIN cryptocurrencies c ON cm.cryptocurrency_id = c.id;

-- ================================================
-- Triggers
-- ================================================
-- Function to log price history
CREATE OR REPLACE FUNCTION update_price_history()
    RETURNS TRIGGER AS $$
DECLARE
    last_close NUMERIC(18, 8);
    high NUMERIC(18, 8);
    low NUMERIC(18, 8);
BEGIN
    -- Récupérer le dernier prix de clôture
    SELECT close INTO last_close
    FROM price_history
    WHERE cryptocurrency_id = NEW.id
    ORDER BY record_date DESC
    LIMIT 1;

    -- Si aucun historique, initialiser à NEW.unit_price
    IF NOT FOUND THEN
        last_close := NEW.unit_price;
    END IF;

    -- Générer high et low avec une variation aléatoire
    high := last_close * (1 + (random() * 0.02)); -- Jusqu'à +2%
    low := last_close * (1 - (random() * 0.02));  -- Jusqu'à -2%

    -- Insérer la nouvelle entrée OHLC
    INSERT INTO price_history (cryptocurrency_id, open, high, low, close)
    VALUES (NEW.id, last_close, high, low, NEW.unit_price);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to log price history after insert or update
CREATE TRIGGER price_history_trigger
    AFTER INSERT OR UPDATE ON cryptocurrencies
    FOR EACH ROW
EXECUTE FUNCTION update_price_history();
