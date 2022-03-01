CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS tb_invitation (
    uuid_hash UUID NOT NULL DEFAULT uuid_generate_v1(),
    CONSTRAINT uuid_hash_tb_invitation PRIMARY KEY (uuid_hash),
    id_group INT NOT NULL,
    used INT NOT NULL,
    dat_creation DATE NOT NULL,
    FOREIGN KEY (id_group)
      REFERENCES tb_group (id_group)
    );

ALTER TABLE tb_person RENAME desc_password TO des_password;

ALTER TABLE tb_person ADD CONSTRAINT uniq_email_person UNIQUE (des_email);

