CREATE TABLE users_(
   id INTEGER,
   name VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   created_at TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE roles(
   id INTEGER,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE cryptocurrencies(
   id INTEGER,
   name VARCHAR(50)  NOT NULL,
   symbol VARCHAR(50)  NOT NULL,
   _created_at TIMESTAMP,
   _updated_at TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE _transaction_types(
   id INTEGER,
   name VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE fund_transactions(
   id INTEGER,
   amount NUMERIC(18,8)  ,
   _transaction_date_ TIMESTAMP,
   status VARCHAR(50) ,
   PRIMARY KEY(id)
);

CREATE TABLE crypto_transactions(
   id INTEGER,
   quantity NUMERIC(18,8)  ,
   _price_unit NUMERIC(18,8)  ,
   _total_amount NUMERIC(18,8)  ,
   transaction_date TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE user_balance_snapshots(
   id VARCHAR(50) ,
   __fund_balance NUMERIC(18,8)  ,
   snapshot_date TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE user_crypto_balances(
   id INTEGER,
   __balance NUMERIC(18,8)  ,
   PRIMARY KEY(id)
);

CREATE TABLE rice_history(
   id INTEGER,
   _open_ NUMERIC(18,8)  ,
   _high NUMERIC(18,8)  ,
   __low NUMERIC(18,8)  ,
   _close NUMERIC(18,8)  ,
   _change NUMERIC(18,8)  ,
   record_date TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE user_roles(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES users_(id),
   FOREIGN KEY(id_1) REFERENCES roles(id)
);

CREATE TABLE Asso_6(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES users_(id),
   FOREIGN KEY(id_1) REFERENCES fund_transactions(id)
);

CREATE TABLE Asso_7(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES _transaction_types(id),
   FOREIGN KEY(id_1) REFERENCES fund_transactions(id)
);

CREATE TABLE Asso_8(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES _transaction_types(id),
   FOREIGN KEY(id_1) REFERENCES crypto_transactions(id)
);

CREATE TABLE Asso_9(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES cryptocurrencies(id),
   FOREIGN KEY(id_1) REFERENCES crypto_transactions(id)
);

CREATE TABLE Asso_10(
   id INTEGER,
   id_1 VARCHAR(50) ,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES users_(id),
   FOREIGN KEY(id_1) REFERENCES user_balance_snapshots(id)
);

CREATE TABLE Asso_13(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES users_(id),
   FOREIGN KEY(id_1) REFERENCES user_crypto_balances(id)
);

CREATE TABLE Asso_14(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES cryptocurrencies(id),
   FOREIGN KEY(id_1) REFERENCES user_crypto_balances(id)
);

CREATE TABLE Asso_15(
   id VARCHAR(50) ,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES user_balance_snapshots(id),
   FOREIGN KEY(id_1) REFERENCES user_crypto_balances(id)
);

CREATE TABLE Asso_16(
   id INTEGER,
   id_1 INTEGER,
   PRIMARY KEY(id, id_1),
   FOREIGN KEY(id) REFERENCES cryptocurrencies(id),
   FOREIGN KEY(id_1) REFERENCES rice_history(id)
);
