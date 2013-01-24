# --- !Ups

CREATE TABLE hb_progress (
  id SERIAL NOT NULL PRIMARY KEY,
  mission_id INT NOT NULL,
  team_id INT NOT NULL,
  prog TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  modified_at TIMESTAMP
);

# --- !Downs

DROP TABLE hb_progress;
