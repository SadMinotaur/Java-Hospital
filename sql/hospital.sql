DROP DATABASE IF EXISTS hospital;
CREATE DATABASE `hospital`;
USE `hospital`;

CREATE TABLE user (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) DEFAULT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    userType ENUM('PATIENT', 'ADMINISTRATOR','DOCTOR')
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE session (
	idUser INT(11) PRIMARY KEY NOT NULL UNIQUE,
    cookie VARCHAR(50) NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (id) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE administrator (
    idUser INT(11) PRIMARY KEY NOT NULL,
    position VARCHAR(50) NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (id) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE medicalSpeciality (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE room (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE doctor (
    idUser INT(11) PRIMARY KEY NOT NULL,
    idRoom INT(11) NOT NULL UNIQUE,
    duration INT(11) NOT NULL,
    idSpeciality INT(11) NOT NULL,
    FOREIGN KEY (idSpeciality) REFERENCES medicalSpeciality (id),
    FOREIGN KEY (idRoom) REFERENCES room (id),
    FOREIGN KEY (idUser) REFERENCES user (id) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE patient (
	idUser INT(11) PRIMARY KEY NOT NULL,
    phone VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES user (id) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE schedule (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idDoctor INT(11) NOT NULL,
    date DATE NOT NULL,
    workingHoursStart TIME NOT NULL,
    workingHoursEnd TIME NOT NULL,
    FOREIGN KEY (idDoctor) REFERENCES doctor (idUser) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE timeSlot (
    ticket VARCHAR(50) NOT NULL,
    idSchedule INT(11) NOT NULL,
    idPatient INT(11),
    timeStart TIME NOT NULL,
    timeEnd TIME NOT NULL,
    state ENUM ('EMPTY','BUSY'),
    FOREIGN KEY (idPatient) REFERENCES patient (idUser) ON delete CASCADE,
    FOREIGN KEY (idSchedule) REFERENCES schedule (id) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE commission (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ticket VARCHAR(50) NOT NULL,
    idPatient INT(11) NOT NULL,
	idRoom INT(11) NOT NULL,
    date DATE NOT NULL,
    timeStart TIME NOT NULL,
    timeEnd TIME NOT NULL,
	FOREIGN KEY (idRoom) REFERENCES room (id) ON delete CASCADE,
	FOREIGN KEY (idPatient) REFERENCES patient (idUser) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE doctorCommission (
	idCom INT(11) NOT NULL,
    idDoctor INT(11) NOT NULL,
    FOREIGN KEY (idCom) REFERENCES commission (id) ON delete CASCADE,
	FOREIGN KEY (idDoctor) REFERENCES doctor (idUser) ON delete CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO room (name) VALUES ('first');
INSERT INTO room (name) VALUES ('second');
INSERT INTO room (name) VALUES ('third');
INSERT INTO medicalSpeciality (name) VALUES ('test');
INSERT INTO medicalSpeciality (name) VALUES ('test1');
INSERT INTO user (firstname, lastname, login, password, userType) VALUES ('admin','admin','aDmiN','admin','ADMINISTRATOR');
INSERT INTO administrator (idUser, position) VALUES (1,'admin');