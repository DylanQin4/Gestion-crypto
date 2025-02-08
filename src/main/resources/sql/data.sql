INSERT INTO cryptocurrencies (name, symbol, image_url)
VALUES
    ('Bitcoin', 'BTC', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/z19safuqt2lvqwvqupsv.png'),
    ('Ethereum', 'ETH', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/gqyc3sk4erefol7dmyej.png'),
    ('Binance Coin', 'BNB', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/vaer6thjlacci4psr5hi.png'),
    ('Cardano', 'ADA', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/rrkyf6msunmmorn926tx.png'),
    ('Solana', 'SOL', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/ralsmzrefxxr5qx9kkeq.png'),
    ('Ripple', 'XRP', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/whobt79xxxtvws5aozlh.png'),
    ('Dogecoin', 'DOGE', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/rcu3exkrfkrxntbmwpxt.png'),
    ('Polkadot', 'DOT', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/wmzfvzq0qybwlb1llhoi.png'),
    ('Litecoin', 'LTC', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/tckgwa5muhiwjk45iep4.png'),
    ('Chainlink', 'LINK', 'https://res.cloudinary.com/dszwvr77q/image/upload/v1738999451/crypto/gllxbuemb9iougag2nxx.png');

INSERT INTO transaction_types (name) VALUES
                                         ('DEPOSIT'),
                                         ('WITHDRAWAL'),
                                         ('BUY'),
                                         ('SELL');

INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('USER');
