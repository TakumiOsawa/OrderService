CREATE DATABASE IF NOT EXISTS eventuate;

USE eventuate;

CREATE TABLE eventuate.message
(
    id varchar(128) NOT NULL,
    destination varchar(128) NOT NULL,
    headers varchar(128) NOT NULL,
    payload varchar(512) NOT NULL,
    creation_time timestamp NOT NULL,
    published int NOT NULL,
    PRIMARY KEY (order_id)
);