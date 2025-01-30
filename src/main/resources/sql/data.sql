INSERT INTO roles (name) VALUES 
('Admin'),
('User'),
('Trader');

INSERT INTO users (name, email) VALUES 
('Alice Dupont', 'alicedupont@gmail.com'),
('Bob Martin', 'bobmartin@gmail.com'),
('Charlie Durand', 'charliedurand@gmail.com');

INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- Alice est Admin
(2, 2), -- Bob est User
(3, 3); -- Charlie est Trader



INSERT INTO cryptocurrencies (name, symbol, unit_price) VALUES 
('Bitcoin', 'BTC', 45000.12345678),
('Ethereum', 'ETH', 3000.98765432),
('Binance Coin', 'BNB', 400.56789012),
('Cardano', 'ADA', 1.23456789),
('Solana', 'SOL', 150.87654321),
('Ripple', 'XRP', 0.56789012),
('Dogecoin', 'DOGE', 0.09876543),
('Polkadot', 'DOT', 25.45678901),
('Litecoin', 'LTC', 180.34567890),
('Chainlink', 'LINK', 15.67890123);


