CREATE DATABASE IF NOT EXISTS eventuate;

USE eventuate;

CREATE TABLE message
(
    id varchar NOT NULL,
    destination varchar NOT NULL,
    headers varchar NOT NULL,
    payload varchar NOT NULL,
    creation_time timestamp NOT NULL,
    published int NOT NULL,
    PRIMARY KEY (order_id)
);