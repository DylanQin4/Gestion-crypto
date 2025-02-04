-- Table des utilisateurs
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des rôles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Table des cryptomonnaies
CREATE TABLE cryptocurrencies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des types de transactions
CREATE TABLE transaction_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Table des transactions de fond (depot et retrait)
CREATE TABLE fund_transactions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    transaction_type_id INT NOT NULL REFERENCES transaction_types(id),
    status VARCHAR(10) NOT NULL CHECK (status IN ('PENDING', 'VALIDATE', 'DELETED')) DEFAULT 'PENDING',
    amount NUMERIC(18, 8) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des transactions en cryptomonnaie (achat et vente)
CREATE TABLE crypto_transactions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    transaction_type_id INT NOT NULL REFERENCES transaction_types(id),
    cryptocurrency_id INT NOT NULL REFERENCES cryptocurrencies(id),
    quantity NUMERIC(18, 8) NOT NULL CHECK (quantity > 0),
    price_unit NUMERIC(18, 8) NOT NULL CHECK (price_unit > 0),
    total_amount NUMERIC(18, 8) NOT NULL CHECK (total_amount > 0),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des snapshots pour stocker les soldes des utilisateurs a moment precis
-- Pour eviter de parcourir toutes les transactions pour calculer le solde
CREATE TABLE user_balance_snapshots (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    fund_balance NUMERIC(18, 8) NOT NULL DEFAULT 0,
    snapshot_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, snapshot_date)
);
CREATE TABLE user_crypto_balances (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    cryptocurrency_id INT NOT NULL REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
    balance NUMERIC(18, 8) NOT NULL DEFAULT 0,
    snapshot_id INT NOT NULL REFERENCES user_balance_snapshots(id) ON DELETE CASCADE,
    UNIQUE (user_id, cryptocurrency_id, snapshot_id)
);

-- Table de l'historique des prix des cryptomonnaies
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


-- SELECT * FROM price_history WHERE cryptocurrency_id = 1 ORDER BY record_date DESC LIMIT 1;

TRUNCATE TABLE price_history;
ALTER SEQUENCE price_history_id_seq RESTART WITH 1;
--
-- DROP TABLE price_history;
-- DROP TABLE cryptocurrencies;
-- DROP TABLE user_roles;
-- DROP TABLE roles;
-- DROP VIEW v_fund;
-- DROP VIEW v_sum_withdrawal;
-- DROP VIEW v_sum_depot;
-- DROP TABLE users CASCADE;
-- DROP TABLE transactionS CASCADE;

-- ================================================
-- VIEWS
-- ================================================

CREATE OR REPLACE VIEW v_users_total_fund AS
SELECT
    ft.user_id,
    SUM(
            CASE
                WHEN ft.transaction_type_id = (SELECT id FROM transaction_types WHERE name = 'DEPOSIT') THEN ft.amount
                WHEN ft.transaction_type_id = (SELECT id FROM transaction_types WHERE name = 'WITHDRAWAL') THEN -ft.amount
                ELSE 0
                END
    ) AS total_funds_amount
FROM
    fund_transactions ft
WHERE ft.status = 'VALIDATE'
GROUP BY
    ft.user_id
ORDER BY
    ft.user_id;

CREATE OR REPLACE VIEW v_users_total_sales AS
SELECT
    ct.user_id,
    SUM(ct.total_amount) AS total_sales_amount
FROM
    crypto_transactions ct
        JOIN
    users u ON ct.user_id = u.id
        JOIN
    transaction_types tt ON ct.transaction_type_id = tt.id
WHERE
    tt.name = 'SELL'
GROUP BY
    ct.user_id
ORDER BY
    ct.user_id;


DROP VIEW v_users_total_buys;
CREATE OR REPLACE VIEW v_users_total_buys AS
SELECT
    ct.user_id,
    SUM(ct.total_amount) AS total_buys_amount
FROM
    crypto_transactions ct
        JOIN
    users u ON ct.user_id = u.id
        JOIN
    transaction_types tt ON ct.transaction_type_id = tt.id
WHERE
    tt.name = 'BUY'
GROUP BY
    ct.user_id
ORDER BY
    ct.user_id;

DROP VIEW v_wallet;
CREATE OR REPLACE VIEW v_wallet AS
SELECT
    u.id AS user_id,
    u.name,
    COALESCE(f.total_funds_amount, 0) AS total_funds_amount,
    COALESCE(s.total_sales_amount, 0) AS total_sales_amount,
    COALESCE(b.total_buys_amount, 0) AS total_buys_amount
FROM
    users u
        LEFT JOIN v_users_total_fund f ON u.id = f.user_id
        LEFT JOIN v_users_total_sales s ON u.id = s.user_id
        LEFT JOIN v_users_total_buys b on u.id = b.user_id
ORDER BY
    u.id;


CREATE OR REPLACE VIEW v_users_crypto_quantity AS
SELECT
    u.id AS user_id,
    c.id AS crypto_id,
    COALESCE(SUM(
         CASE
             WHEN ct.transaction_type_id = (SELECT id FROM transaction_types WHERE name = 'BUY') THEN ct.quantity
             WHEN ct.transaction_type_id = (SELECT id FROM transaction_types WHERE name = 'SELL') THEN -ct.quantity
             ELSE 0
             END
     ), 0) AS total_quantity
FROM
    users u
        CROSS JOIN cryptocurrencies c -- assure tous les combinaisons user/crypto
        LEFT JOIN crypto_transactions ct ON ct.user_id = u.id AND ct.cryptocurrency_id = c.id
GROUP BY
    u.id, c.id
ORDER BY
    u.id, c.id;

-- ================================================
-- Triggers
-- ================================================
