CREATE DATABASE IF NOT EXISTS ads_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ads_platform;

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_phone;
DROP TABLE IF EXISTS language;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS advertisement_category;
DROP TABLE IF EXISTS advertisement;

CREATE TABLE role (
role_id smallint(3) unsigned NOT NULL AUTO_INCREMENT,
role_name varchar(30) NOT NULL,
UNIQUE KEY (role_name),
PRIMARY KEY (role_id));


CREATE TABLE user (
user_id int(10) unsigned NOT NULL AUTO_INCREMENT,
role_id smallint(3) unsigned NOT NULL,
login varchar(20) NOT NULL,
password varchar(40) NOT NULL,
first_name varchar(30) NOT NULL,
last_name varchar(30) NOT NULL,
email varchar(50) NOT NULL,
created_date datetime NOT NULL,
ban boolean NOT NULL default 0,
PRIMARY KEY (user_id),
UNIQUE KEY (login),
UNIQUE KEY (email),
FOREIGN KEY (role_id) REFERENCES role (role_id) ON DELETE CASCADE ON UPDATE RESTRICT);


CREATE TABLE user_phone (
id int(10) unsigned NOT NULL AUTO_INCREMENT,
user_id int(10) unsigned NOT NULL,
phone_number varchar(14) NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY (phone_number),
FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT);


CREATE TABLE language (
language_id smallint(3) unsigned NOT NULL AUTO_INCREMENT,
language_name varchar(30) NOT NULL,
UNIQUE KEY (language_name),
PRIMARY KEY (language_id));


CREATE TABLE location (
location_id int(10) unsigned NOT NULL AUTO_INCREMENT,
parent_id int(10) unsigned,
language_id smallint(3) unsigned NOT NULL,
location_name varchar(40) NOT NULL,
PRIMARY KEY (location_id, language_id),
FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE ON UPDATE RESTRICT);


CREATE TABLE category (
category_id smallint(3) unsigned NOT NULL AUTO_INCREMENT,
language_id smallint(3) unsigned NOT NULL,
category_name varchar(30) NOT NULL,
PRIMARY KEY (category_id, language_id),
FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE ON UPDATE RESTRICT);


CREATE TABLE advertisement (
advertisement_id int(10) unsigned NOT NULL AUTO_INCREMENT,
user_id int(10) unsigned NOT NULL,
advertisement_title varchar(40) NOT NULL,
description text NOT NULL,
location_id int(10) unsigned NOT NULL,
posted_date datetime NOT NULL,
category_id smallint(3) unsigned NOT NULL,
price int(10),
PRIMARY KEY (advertisement_id),
FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT,
FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE ON UPDATE RESTRICT);

CREATE TABLE advertisement_image (
image_id int(10) unsigned NOT NULL AUTO_INCREMENT,
advertisement_id int(10) unsigned NOT NULL,
image_path varchar(80) NOT NULL,
PRIMARY KEY (image_id));


CREATE TABLE comment (
comment_id int(10) unsigned NOT NULL AUTO_INCREMENT,
advertisement_id int(10) unsigned NOT NULL,
user_id int(10) unsigned NOT NULL,
description tinytext NOT NULL,
posted_date datetime NOT NULL,
PRIMARY KEY (comment_id),
FOREIGN KEY (advertisement_id) REFERENCES advertisement (advertisement_id) ON DELETE CASCADE ON UPDATE RESTRICT,
FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT);


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE get_ads_by_user_id (in user_id INT(10))
BEGIN
SELECT * FROM advertisement where user_id = user_id;
END //
DELIMITER ;


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE add_new_user (
														in role_id smallint(3),
														in login varchar(20),
														in password varchar(40),
														in first_name varchar(30),
														in last_name varchar(30),
														in email varchar(50),
														in created_date datetime,
														in ban boolean,
														in phone_number varchar(14),
														out result int(10))
BEGIN

DECLARE new_user_id int(10) DEFAULT 0;
DECLARE result int default 0; 
START TRANSACTION;

INSERT INTO user (role_id, login, password, first_name, last_name, email, created_date, ban)
			VALUES (role_id, login, password, first_name, last_name, email, created_date, ban);

SET new_user_id = LAST_INSERT_ID();
IF new_user_id > 0 THEN
	INSERT INTO user_phone (user_id, phone_number)
			VALUES (new_user_id, phone_number);
	COMMIT;
	SELECT ROW_COUNT() INTO result; 
ELSE
	ROLLBACK;
END IF;

END //
DELIMITER ;



DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE get_ad_by_id (in advertisement_id INT(10))
BEGIN
SELECT * FROM advertisement where advertisement_id = advertisement_id;
END //
DELIMITER ;


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE get_phonenumber_by_user_id (in user_id INT(10))
BEGIN
SELECT phone_number FROM user_phone where user_id = user_id;
END //
DELIMITER ;


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE get_ads_by_location_id (in location_id INT(10))
BEGIN
SELECT * FROM advertisement where location_id = location_id;
END //
DELIMITER ;


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE get_ads_by_category_id (in category_id INT(10))
BEGIN
SELECT * FROM advertisement where category_id = category_id;
END //
DELIMITER ;


INSERT INTO role VALUES (1, 'administrator');
INSERT INTO role VALUES (2, 'customer');
INSERT INTO user VALUES (1, 2,'user', 'b5b73fae0d87d8b4e2573105f8fbe7bc', 'Jim', 'Halpert', 'jim@email.com', '2020-11-21 20:46:38', 0);
INSERT INTO user VALUES (NULL, 2,'user2', 'a738f1b9318bcd0c1f1731ab6d3b1e64', 'Kevin', 'Malone', 'kevin@email.com', '2020-11-21 20:46:38', 0);
INSERT INTO user VALUES (NULL, 1,'admin', 'c93ccd78b2076528346216b3b2f701e6', 'Admin', 'adminov', 'adminov@email.com', '2020-11-21 20:46:38', 0);
INSERT INTO user_phone VALUES (NULL, 1, '+77011820844');
INSERT INTO user_phone VALUES (NULL, 1, '+77471820844');
INSERT INTO user_phone VALUES (NULL, 2, '+77472222142');
INSERT INTO language VALUES (1, 'ru');
INSERT INTO language VALUES (2, 'en');

INSERT INTO location VALUES (1, NULL, 1, 'Казахстан');
INSERT INTO location VALUES (2, 1, 1, 'Нур-Султан');
INSERT INTO location VALUES (3, 1, 1, 'Алматы');
INSERT INTO location VALUES (4, 1, 1, 'Шымкент');
INSERT INTO location VALUES (5, 1, 1, 'Актобе');
INSERT INTO location VALUES (6, 1, 1, 'Караганды');
INSERT INTO location VALUES (7, 1, 1, 'Тараз');
INSERT INTO location VALUES (8, 1, 1, 'Павлодар');
INSERT INTO location VALUES (9, 1, 1, 'Усть-Каменогорск ');
INSERT INTO location VALUES (10, 1, 1, 'Семей');
INSERT INTO location VALUES (11, 1, 1, 'Атырау');
INSERT INTO location VALUES (12, 1, 1, 'Костанай');
INSERT INTO location VALUES (13, 1, 1, 'Кызылорда');
INSERT INTO location VALUES (14, 1, 1, 'Уральск');
INSERT INTO location VALUES (15, 1, 1, 'Петропавловск');
INSERT INTO location VALUES (16, 1, 1, 'Актау');

INSERT INTO location VALUES (1, NULL, 2, 'Kazakhstan');
INSERT INTO location VALUES (2, 1, 2, 'Nur-Sultan');
INSERT INTO location VALUES (3, 1, 2, 'Almaty');
INSERT INTO location VALUES (4, 1, 2, 'Shymkent');
INSERT INTO location VALUES (5, 1, 2, 'Aktobe');
INSERT INTO location VALUES (6, 1, 2, 'Karagandy');
INSERT INTO location VALUES (7, 1, 2, 'Taraz');
INSERT INTO location VALUES (8, 1, 2, 'Pavlodar');
INSERT INTO location VALUES (9, 1, 2, 'Oskemen');
INSERT INTO location VALUES (10, 1, 2, 'Semey');
INSERT INTO location VALUES (11, 1, 2, 'Atyrau');
INSERT INTO location VALUES (12, 1, 2, 'Kostanay');
INSERT INTO location VALUES (13, 1, 2, 'Kyzylorda');
INSERT INTO location VALUES (14, 1, 2, 'Oral');
INSERT INTO location VALUES (15, 1, 2, 'Petropavl');
INSERT INTO location VALUES (16, 1, 2, 'Aktau');

INSERT INTO category VALUES (1, 1, 'Работа');
INSERT INTO category VALUES (2, 1, 'Недвижимость');
INSERT INTO category VALUES (3, 1, 'Электроника');
INSERT INTO category VALUES (4, 1, 'Услуги');
INSERT INTO category VALUES (5, 1, 'Транспорт');
INSERT INTO category VALUES (1, 2, 'Job');
INSERT INTO category VALUES (2, 2, 'Real estate');
INSERT INTO category VALUES (3, 2, 'Electronics');
INSERT INTO category VALUES (4, 2, 'Services');
INSERT INTO category VALUES (5, 2, 'Transport');



INSERT INTO advertisement VALUES (1, 1, 'Требуется продавец', 'Требуется продавец в очень большую компанию. Срочно!', 2, '2020-11-21 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (2, 1, 'Требуется грузчик', 'Требуется грузчик в очень большую компанию. Срочно!', 3, '2020-11-21 20:46:38',1, NULL);

INSERT INTO advertisement VALUES (NULL, 2, 'Продам Новый Компьютер - Моноблок', 'Продам Очень Быстрый Компьютер Всё В Одном. Моноблок. С отличными характеристиками. Диагональ 24 дюйма. Изогный.FULL HD IPS дисплей с потрясающим углом обзора и шикарной цветопередачей. Полноценный компьютерный четырехъядерный процессор Core i5 с базовой частотой 3.2 гигагерца.', 2, '2020-11-21 20:46:38', 3, 199990);
INSERT INTO advertisement VALUES (NULL, 2, 'Топовый процессор AMD Ryzen 5600x ', 'Топовый игровой процессор AMD Ryzen 5 5600x box версия. Новое поступление В наличии 2 шт', 3, '2020-11-21 20:46:38',3, 170000);
INSERT INTO advertisement VALUES (NULL, 2, 'Мерседес бенз 190', 'Продам мерс на активном ходу, мотор работает как часы весной кап ремонт делали, коробка передач 5 стубка привозной, редуктор привозной. На летнем резине. Вложение надо дазатор почистить а так на ходу ', 2, '2020-12-21 20:46:38',5, 790000);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется разработчик', 'Требуется разработчик в очень большую компанию. Срочно!', 2, '2020-12-21 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется прораб', 'Требуется прораб в очень большую компанию. Срочно!', 3, '2020-11-11 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется водитель', 'Требуется водитель в очень большую компанию. Срочно!', 2, '2019-11-21 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется учитель', 'Требуется учитель в очень большую компанию. Срочно!', 3, '2020-11-21 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется директор', 'Требуется директор в очень большую компанию. Срочно!', 2, '2017-12-21 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Требуется менеджер', 'Требуется менеджер в очень большую компанию. Срочно!', 3, '2018-10-11 20:46:38',1, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Продается дом', 'Продается дом в районе Шубары', 2, '2017-12-21 20:46:38',2, NULL);
INSERT INTO advertisement VALUES (NULL, 1, 'Продается квартира', 'Продается квартира в районе Шубары', 3, '2018-10-11 20:46:38',2, NULL);
INSERT INTO comment VALUES (NULL, 1, 1, 'Я здесь написал коммент.', '2020-11-21 20:46:38');
INSERT INTO comment VALUES (NULL,1, 1, 'Мой второй коммент!', '2020-11-21 20:47:38');
