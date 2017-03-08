CREATE DATABASE my_community_db;
USE my_community_db;
CREATE TABLE users (
  first_name VARCHAR(50)         NOT NULL,
  last_name  VARCHAR(50)         NOT NULL,
  address    VARCHAR(200),
  email      VARCHAR(50) PRIMARY KEY   NOT NULL UNIQUE,
  password   VARCHAR(20)         NOT NULL
);

CREATE TABLE persona (
  email VARCHAR(50) REFERENCES users.email,
  interest VARCHAR(50) REFERENCES interests.title # e.g. vegetarian, basketball
);

CREATE TABLE events (
  name VARCHAR(50) PRIMARY KEY NOT NULL, # name of place, e.g. Veggie Grill student discount, UCI basketball VS. UCSD basketball
  interest VARCHAR(50) REFERENCES persona.interest
);
