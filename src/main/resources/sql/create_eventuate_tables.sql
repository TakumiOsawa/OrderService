CREATE DATABASE IF NOT EXISTS eventuate;

USE eventuate;

DROP TABLE IF EXISTS eventuate.message;

CREATE TABLE eventuate.message
(
    id varchar(64) NOT NULL,
    destination varchar(64) NOT NULL,
    headers varchar(512) NOT NULL,
    payload varchar(512) NOT NULL,
    creation_time varchar(32) NOT NULL,
    published int NOT NULL,
    PRIMARY KEY (id)
);