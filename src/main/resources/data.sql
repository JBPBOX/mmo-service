DROP TABLE IF EXISTS profile_to_attribute;
DROP TABLE IF EXISTS profile_to_rank;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS attribute;
DROP TABLE IF EXISTS equipement;
DROP TABLE IF EXISTS kda;
DROP TABLE IF EXISTS bank_account;
DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS rank;
DROP TABLE IF EXISTS player;

CREATE TABLE player (
  id_player SERIAL PRIMARY KEY,
  username varchar(16) NOT NULL,
  password text NOT NULL,
  uuid uuid NOT NULL,
  role varchar(32) NOT NULL,
  creation_date TIMESTAMP,
  is_active boolean NOT NULL DEFAULT true
);

CREATE TABLE rank (
  id_rank SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL,
  level INTEGER NOT NULL,
  minimum_player_xp_level INTEGER NOT NULL
);

CREATE TABLE profile (
  id_profile SERIAL PRIMARY KEY,
  player_id INTEGER NOT NULL,
  rank_id INTEGER NOT NULL,
  profile_name varchar(100) NOT NULL,
  creation_date TIMESTAMP,
  level INTEGER NOT NULL,
  progression INTEGER NOT NULL,
  is_active boolean NOT NULL DEFAULT true,
  FOREIGN KEY (player_id) REFERENCES player(id_player) ON DELETE CASCADE,
  FOREIGN KEY (rank_id) REFERENCES rank(id_rank) ON DELETE CASCADE
);

CREATE TABLE bank_account (
  id_bank_account SERIAL PRIMARY KEY,
  profile_id INTEGER NOT NULL,
  amount INTEGER NOT NULL,
  is_active boolean NOT NULL DEFAULT true,
  FOREIGN KEY (profile_id) REFERENCES profile(id_profile) ON DELETE CASCADE
);

CREATE TABLE kda (
  id_kda SERIAL PRIMARY KEY,
  profile_id INTEGER NOT NULL,
  kill INTEGER NOT NULL,
  death INTEGER NOT NULL,
  assist INTEGER NOT NULL,
  FOREIGN KEY (profile_id) REFERENCES profile(id_profile) ON DELETE CASCADE
);

CREATE TABLE equipement (
  id_equipement SERIAL PRIMARY KEY,
  profile_id INTEGER NOT NULL,
  inventory_slot INTEGER NOT NULL,
  item text NOT NULL,
  FOREIGN KEY (profile_id) REFERENCES profile(id_profile) ON DELETE CASCADE
);

CREATE TABLE attribute (
  id_attribute SERIAL PRIMARY KEY,
  name varchar(100) NOT NULL,
  level INTEGER NOT NULL
);

CREATE TABLE transaction (
  id_transaction SERIAL PRIMARY KEY,
  giver_bank_account INTEGER NOT NULL,
  receiver_bank_account INTEGER NOT NULL,
  amount INTEGER NOT NULL,
  transaction_date TIMESTAMP,
  FOREIGN KEY (giver_bank_account) REFERENCES bank_account(id_bank_account) ON DELETE CASCADE,
  FOREIGN KEY (receiver_bank_account) REFERENCES bank_account(id_bank_account) ON DELETE CASCADE
);

CREATE TABLE profile_to_attribute (
  attribute_id INTEGER NOT NULL,
  profile_id INTEGER NOT NULL,
  level INTEGER NOT NULL,
  FOREIGN KEY (attribute_id) REFERENCES attribute(id_attribute) ON DELETE CASCADE,
  FOREIGN KEY (profile_id) REFERENCES profile(id_profile) ON DELETE CASCADE,
  PRIMARY KEY (attribute_id, profile_id)
);