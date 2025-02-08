-- Désactiver les triggers et contraintes pour éviter les conflits
SET session_replication_role = replica;

-- Supprimer toutes les données des tables (ordre inversé des dépendances)
TRUNCATE TABLE
    user_crypto_balances,
    user_balance_snapshots,
    crypto_transactions,
    fund_transactions,
    price_history,
    user_roles,
    users,
    roles,
    cryptocurrencies,
    transaction_types
    RESTART IDENTITY CASCADE;

-- Réactiver les triggers et contraintes
SET session_replication_role = DEFAULT;

-- Réinitialiser manuellement toutes les séquences au cas où
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;
ALTER SEQUENCE cryptocurrencies_id_seq RESTART WITH 1;
ALTER SEQUENCE transaction_types_id_seq RESTART WITH 1;
ALTER SEQUENCE fund_transactions_id_seq RESTART WITH 1;
ALTER SEQUENCE crypto_transactions_id_seq RESTART WITH 1;
ALTER SEQUENCE user_balance_snapshots_id_seq RESTART WITH 1;
ALTER SEQUENCE user_crypto_balances_id_seq RESTART WITH 1;
ALTER SEQUENCE price_history_id_seq RESTART WITH 1;