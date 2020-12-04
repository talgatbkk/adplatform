CREATE DATABASE IF NOT EXISTS ads_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ads_platform;

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
customer_id int(10) unsigned NOT NULL AUTO_INCREMENT,
login varchar(20) NOT NULL,
password varchar(40) NOT NULL,
first_name varchar(30) NOT NULL,
last_name varchar(30) NOT NULL,
email varchar(50) NOT NULL,
created_date datetime NOT NULL,
PRIMARY KEY (customer_id),
UNIQUE KEY (customer_id),
UNIQUE KEY (login));

INSERT INTO customer VALUES (1,'takha', 'passwordtest123', 'Талгат', 'Бекк', 'talgat@email.com', '2020-11-21 20:46:38');



DROP TABLE IF EXISTS administrator;

CREATE TABLE administrator (
administrator_id int(10) unsigned NOT NULL AUTO_INCREMENT,
login varchar(20) NOT NULL,
password varchar(40) NOT NULL,
first_name varchar(30) NOT NULL,
last_name varchar(30) NOT NULL,
email varchar(50) NOT NULL,
created_date datetime NOT NULL,
PRIMARY KEY (administrator_id),
UNIQUE KEY (administrator_id),
UNIQUE KEY (login));

INSERT INTO administrator VALUES (1,'Admin_takha', 'Admin_passwordtest123', 'ТалгатAdmin', 'Бекк', 'talgat@email.com', '2020-11-21 20:46:38');



DROP TABLE IF EXISTS customer_phone;
CREATE TABLE customer_phone (
customer_id int(10) unsigned NOT NULL,
phone_number varchar(25) NOT NULL,
PRIMARY KEY (customer_id, phone_number),
UNIQUE KEAdmin_Y (phone_number),
FOREIGNAdmin KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE);

INSERT INTO customer_phone VALUES (1, '+77011820844');
INSERT INTO customer_phone VALUES (1, '+77471820844');



DROP TABLE IF EXISTS language;
CREATE TABLE language (
language_id smallint(3) unsigned NOT NULL AUTO_INCREMENT,
language_name varchar(30) NOT NULL,
UNIQUE KEY (language_name),
PRIMARY KEY (language_id));

INSERT INTO language VALUES (1, 'rus');
INSERT INTO language VALUES (2, 'en');


DROP TABLE IF EXISTS city;
CREATE TABLE city (
city_id int(10) unsigned NOT NULL AUTO_INCREMENT,
city_name varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
language_id smallint(3) unsigned NOT NULL,
PRIMARY KEY (city_id),
FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE);

INSERT INTO city VALUES (1, 'Астана', 1);



DROP TABLE IF EXISTS region_has_city;
CREATE TABLE region_has_city (
city_id int(10) unsigned NOT NULL,
region_id int(10) unsigned NOT NULL,
language_id smallint(3) unsigned NOT NULL,
region_name varchar(50) NOT NULL,
PRIMARY KEY (city_id, region_id, language_id),
FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE,
FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE);

INSERT INTO region_has_city VALUES (1, 1, 1, 'Акмолинская область');



DROP TABLE IF EXISTS category;
CREATE TABLE category (
category_id smallint(3) unsigned NOT NULL AUTO_INCREMENT,
language_id smallint(3) unsigned NOT NULL,
category_name varchar(30) NOT NULL,
PRIMARY KEY (category_id, language_id),
FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE);
INSERT INTO category VALUES (1, 1, 'Работа');
INSERT INTO category VALUES (1, 2, 'Job');



DROP TABLE IF EXISTS ad;
CREATE TABLE ad (
ad_id int(10) unsigned NOT NULL AUTO_INCREMENT,
customer_id int(10) unsigned NOT NULL,
ad_title varchar(40) NOT NULL,
description text NOT NULL,
city_id int(10) unsigned NOT NULL,
posted_date datetime NOT NULL,
category_id smallint(3) unsigned NOT NULL,
PRIMARY KEY (ad_id),
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE,
FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE);

INSERT INTO ad VALUES (1, 1, 'Требуется продавец', 'Требуется продавец в очень большую компанию. Срочно!', 1, '2020-11-21 20:46:38',1);


DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
ad_id int(10) unsigned NOT NULL,
customer_id int(10) unsigned NOT NULL,
description tinytext NOT NULL,
posted_date datetime NOT NULL,
PRIMARY KEY (ad_id, customer_id, posted_date),
FOREIGN KEY (ad_id) REFERENCES ad (ad_id) ON DELETE CASCADE,
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE);
INSERT INTO comment VALUES (1, 1, 'Я здесь написал коммент.', '2020-11-21 20:46:38');
INSERT INTO comment VALUES (1, 1, 'Мой второй коммент!', '2020-11-21 20:47:38');
