DROP database IF EXISTS `usertestDB`;

create database usertestDB;
use usertestDB;

CREATE TABLE users(
  id INT NOT NULL AUTO_INCREMENT,
  firstname varchar(30) NOT NULL,
  lastname varchar(30) NOT NULL,
  username varchar(30) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(256) NOT NULL,
  enabled bool,
  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT UK_email UNIQUE (email),
  CONSTRAINT UK_username UNIQUE (username),
  primary key(id)
);

CREATE TABLE user_roles (
  username varchar(30) NOT NULL,
  role varchar(16) NOT NULL
);
