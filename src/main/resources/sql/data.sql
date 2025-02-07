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

INSERT INTO transaction_types (name) VALUES
                                         ('DEPOSIT'),
                                         ('WITHDRAWAL'),
                                         ('BUY'),
                                         ('SELL');

INSERT INTO users (name, email) VALUES ('Dylan', 'dylanqin@gmail.com');
INSERT INTO users (name, email) VALUES
                                    ('Bob Martin', 'bob.martin@example.com'),
                                    ('Claire Durand', 'claire.durand@example.com');

INSERT INTO users (name, email) VALUES ('Test', 'test@gmail.com');

INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('USER');

INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),  -- Dylan -> ADMIN
                                              (2, 2),  -- Bob Martin -> USER
                                              (3, 2);  -- Claire Durand -> USER

-- Insertion de transactions de fond (dépôt/retrait)
INSERT INTO fund_transactions (user_id, transaction_type_id, status, amount, transaction_date)
VALUES
    (1, 1, 'VALIDATE', 1000.00, '2025-01-01 09:00:00'),
    (2, 1, 'VALIDATE', 500.00, '2025-01-02 10:30:00'), 
    (3, 1, 'VALIDATE', 200.00, '2025-01-03 11:15:00');

INSERT INTO fund_transactions (user_id, transaction_type_id, status, amount, transaction_date)
VALUES
    (1, 2, 'VALIDATE', 100.00, '2025-01-02 09:00:00'),
    (2, 2, 'VALIDATE', 50.00, '2025-01-03 10:30:00'), 
    (3, 2, 'VALIDATE', 20.00, '2025-01-04 11:15:00');

INSERT INTO crypto_transactions (user_id, transaction_type_id, cryptocurrency_id, quantity, price_unit, total_amount, transaction_date)
VALUES
    (1, 3, 1, 0.05, 40000.00, 2000.00, '2025-01-05 14:00:00'),
    (2, 3, 2, 2.0, 2500.00, 5000.00, '2025-01-06 15:30:00'),
    (3, 3, 1, 0.02, 42000.00, 840.00, '2025-01-07 16:45:00');

INSERT INTO crypto_transactions (user_id, transaction_type_id, cryptocurrency_id, quantity, price_unit, total_amount, transaction_date)
VALUES
    (1, 3, 3, 0.05, 40000.00, 2000.00, '2025-01-05 14:00:00'), 
    (2, 3, 4, 2.0, 2500.00, 5000.00, '2025-01-06 15:30:00'),
    (3, 3, 5, 0.02, 42000.00, 840.00, '2025-01-07 16:45:00');

INSERT INTO crypto_transactions (user_id, transaction_type_id, cryptocurrency_id, quantity, price_unit, total_amount, transaction_date)
VALUES
    (1, 4, 3, 0.05, 40000.00, 200.00, '2025-01-08 14:00:00'), 
    (2, 4, 4, 2.0, 2500.00, 500.00, '2025-01-08 15:30:00'),
    (3, 4, 5, 0.02, 42000.00, 80.00, '2025-01-10 16:45:00');



