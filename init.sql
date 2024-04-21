
-- DROP TABLE accounts;

CREATE TABLE accounts (
	account_id serial4 NOT NULL,
	customer_id varchar NOT NULL,
	country varchar(255) NOT NULL,
	CONSTRAINT accounts_pk PRIMARY KEY (account_id)
);


-- public.account_balances definition

-- Drop table

-- DROP TABLE account_balances;

CREATE TABLE account_balances (
	account_id int4 NOT NULL,
	currency varchar(255) NOT NULL,
	amount float8 NOT NULL,
	CONSTRAINT account_balances_fk0 FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);


-- public.transactions definition

-- Drop table

-- DROP TABLE transactions;

CREATE TABLE transactions (
	transaction_id serial4 NOT NULL,
	account_id int4 NOT NULL,
	amount float8 NOT NULL,
	currency varchar(255) NOT NULL,
	transaction_direction varchar(255) NOT NULL,
	description varchar(255) NOT NULL,
	CONSTRAINT transactions_pk PRIMARY KEY (transaction_id),
	CONSTRAINT transactions_fk0 FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
