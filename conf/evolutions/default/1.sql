# --- !Ups

CREATE TABLE hb_team(
  id SERIAL NOT NULL PRIMARY KEY,
  identity TEXT NOT NULL,
  password TEXT NOT NULL,
  name  TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL
);

# --- !Downs

DROP TABLE hb_team;