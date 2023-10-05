CREATE TABLE accounts(
  id uuid NOT NULL,
  name character varying (1024) not null,
  pin character varying (4) not null,
  funds dec not null,
  CONSTRAINT account_id_pk PRIMARY KEY (id),
	CONSTRAINT account_name_uniq UNIQUE (name)
);