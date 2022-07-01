
INSERT INTO company (id, name, balance) Values (1, 'Glady', 20000);
INSERT INTO company (id, name, balance) Values (2, 'Tesla', 500000);
INSERT INTO company (id, name, balance) Values (3, 'Amazon', 1000000);



INSERT INTO users (id, name,  id_company) Values (1, 'Jean', 1);
INSERT INTO users (id, name, id_company) Values (2, 'Robert', 1);
INSERT INTO users (id, name, id_company) Values (3, 'Salim', 2);
INSERT INTO users (id, name, id_company) Values (4, 'Mohamed', 2);
INSERT INTO users (id, name, id_company) Values (5, 'Julien', 3);
INSERT INTO users (id, name, id_company) Values (6, 'Jessica', 3);
INSERT INTO users (id, name, id_company) Values (7, 'Coralie', 3);
INSERT INTO users (id, name, id_company) Values (8, 'Quentin', 2);

drop table walletuser;
create table walletuser (id bigint auto_increment, id_user bigint NOT NULL, balance int NOT NULL, category varchar(250) NOT NULL, created_date date NOT NULL);

INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (1, 1, 100, 'MEAL', '2022-06-28');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (2, 1, 100, 'GIFT', '2022-06-29');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (3, 2, 100, 'GIFT', '2022-06-15');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (4, 3, 100, 'MEAL', '2022-06-30');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (5, 4, 100, 'GIFT', '2022-06-28');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (6, 5, 100, 'MEAL', '2022-07-02');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (7, 6, 100, 'GIFT', '2022-06-01');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (8, 7, 100, 'MEAL', '2022-06-20');
INSERT INTO walletuser (id, id_user, balance, category, created_date) Values (9, 8, 100, 'GIFT', '2022-06-10');



