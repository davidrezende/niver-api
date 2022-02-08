CREATE SEQUENCE person_id_seq;

CREATE TABLE IF NOT EXISTS tb_person (
  id_person integer NOT NULL DEFAULT nextval('person_id_seq'),
  des_name VARCHAR(100) NOT NULL,
  dat_birthday DATE NOT NULL,
  des_email VARCHAR(150) NOT NULL,
  desc_password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_person));

ALTER SEQUENCE person_id_seq OWNED BY tb_person.id_person;

CREATE SEQUENCE group_id_seq;

CREATE TABLE IF NOT EXISTS tb_group (
  id_group integer NOT NULL DEFAULT nextval('group_id_seq'),
  des_name VARCHAR(100) NOT NULL,
  id_owner integer NOT NULL,
  PRIMARY KEY (id_group),
  FOREIGN KEY (id_owner)
      REFERENCES tb_person (id_person)
    );

ALTER SEQUENCE group_id_seq OWNED BY tb_group.id_group;

CREATE TABLE ta_group_members (
  id_group INT NOT NULL,
  id_person INT NOT NULL,
  PRIMARY KEY (id_group, id_person),
  FOREIGN KEY (id_group)
      REFERENCES tb_group (id_group),
  FOREIGN KEY (id_person)
      REFERENCES tb_person (id_person)
); 
