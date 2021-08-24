DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_line_items;

CREATE TABLE orders
(
    order_id bigint NOT NULL,
    state varchar(32) NOT NULL,
    version int NOT NULL,
    consumer_id bigint NOT NULL,
    restaurant_id bigint NOT NULL,
    PRIMARY KEY (order_id)
) WITHOUT OIDS;

CREATE TABLE order_line_items
(
    quantity bigint NOT NULL,
    menu_item_id bigint NOT NULL,
    name varchar(32) NOT NULL,
    price bigint NOT NULL
) WITHOUT OIDS;

COMMENT ON TABLE orders IS '注文';
COMMENT ON COLUMN orders.order_id IS '注文ID';
COMMENT ON COLUMN orders.state IS '注文状態';
COMMENT ON COLUMN orders.version IS 'バージョン';
COMMENT ON COLUMN orders.consumer_id IS '利用者ID';
COMMENT ON COLUMN orders.restaurant_id IS 'レストランID';
COMMENT ON TABLE order_line_items IS 'メニュー';
COMMENT ON COLUMN order_line_items.quantity IS '数量';
COMMENT ON COLUMN order_line_items.menu_item_id IS 'メニューID';
COMMENT ON COLUMN order_line_items.name IS 'メニュー名';
COMMENT ON COLUMN order_line_items.price IS '単価';