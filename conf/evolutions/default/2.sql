# --- !Ups

CREATE TABLE hb_mission(
  id SERIAL NOT NULL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT NOT NULL,
  place TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL
);

# --- !Downs

DROP TABLE hb_mission;