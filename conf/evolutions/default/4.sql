# --- !Ups

CREATE TABLE hb_question(
  id SERIAL NOT NULL PRIMARY KEY,
  kind TEXT NOT NULL,
  question TEXT NOT NULL,
  answer TEXT 
);

# --- !Downs

DROP TABLE hb_question;