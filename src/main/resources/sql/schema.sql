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
    unit_price NUMERIC(18, 8) NOT NULL CHECK (unit_price >= 0),
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

-- Table of Price History (pour la graphique)
CREATE TABLE price_history (
    id SERIAL PRIMARY KEY,
    cryptocurrency_id INT NOT NULL REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
    price NUMERIC(18, 8) NOT NULL CHECK (price >= 0),
    record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================================
-- VIEWS
-- ================================================

-- View of Wallet Balances
CREATE VIEW wallet_balances_view AS
SELECT
    u.name AS users,
    w.fund_balance AS fund_balance,
    COALESCE(SUM(cm.quantity * c.unit_price), 0) AS crypto_value
FROM users u
         JOIN wallets w ON u.id = w.user_id
         LEFT JOIN crypto_movements cm ON w.id = cm.transaction_id
         LEFT JOIN cryptocurrencies c ON cm.cryptocurrency_id = c.id
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
BEGIN
    INSERT INTO price_history (cryptocurrency_id, price)
    VALUES (NEW.id, NEW.unit_price);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to log price history after insert or update
CREATE TRIGGER price_history_trigger
    AFTER INSERT OR UPDATE ON cryptocurrencies
    FOR EACH ROW
EXECUTE FUNCTION update_price_history();

-- ================================================
-- Real-time Price Updates
-- ================================================

-- Function to update cryptocurrency prices
CREATE OR REPLACE FUNCTION update_crypto_prices()
    RETURNS VOID AS $$
DECLARE
    crypto RECORD;
    last_price NUMERIC(18, 8);
    variation DOUBLE PRECISION;
BEGIN
    -- Loop through all cryptocurrencies
    FOR crypto IN
        SELECT id, name FROM cryptocurrencies
        LOOP
            -- Get the last price of the cryptocurrency
            SELECT unit_price INTO last_price
            FROM cryptocurrencies
            WHERE id = crypto.id
            ORDER BY created_at DESC
            LIMIT 1;

            -- If no price found, initialize it to 10000
            IF NOT FOUND THEN
                last_price := 10000;
            END IF;

            -- Calculate the random variation between -1% and +1%
            variation := (random() * 0.02) - 0.01;  -- variation between -1% and +1%

            -- Update the price with the variation
            last_price := last_price * (1 + variation);

            -- Insert the new price into the price history
            INSERT INTO price_history (cryptocurrency_id, price)
            VALUES (crypto.id, last_price);

            -- Update the cryptocurrencies table with the new price
            UPDATE cryptocurrencies
            SET unit_price = last_price,
                updated_at = NOW()
            WHERE id = crypto.id;
        END LOOP;
END;
$$ LANGUAGE plpgsql;

-- View to display real-time prices
CREATE OR REPLACE VIEW real_time_prices_view AS
SELECT
    id,
    name,
    symbol,
    unit_price,
    updated_at
FROM cryptocurrencies
ORDER BY updated_at DESC;

-- ================================================
-- Automation avec pg_cron
-- ================================================
-- SELECT cron.schedule('update_prices', '*/10 * * * *', $$CALL update_crypto_prices()$$);
