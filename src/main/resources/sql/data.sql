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

INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('USER');
