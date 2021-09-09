DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_line_items;

CREATE TABLE orders
(
    order_id bigint NOT NULL,
    state varchar(32) NOT NULL,
    version int NOT NULL,
    consumer_id bigint,
    restaurant_id bigint,
    delivery_time timestamp,
    street1 varchar(32) NOT NULL,
    street2 varchar(32) NOT NULL,
    city varchar(32) NOT NULL,
    address_state varchar(32) NOT NULL,
    zip varchar(16) NOT NULL,
    payment_token varchar(256),
    PRIMARY KEY (order_id)
);

CREATE TABLE order_line_items
(
    order_order_id bigint NOT NULL,
    quantity bigint NOT NULL,
    menu_item_id bigint NOT NULL,
    name varchar(32) NOT NULL,
    price bigint NOT NULL
);