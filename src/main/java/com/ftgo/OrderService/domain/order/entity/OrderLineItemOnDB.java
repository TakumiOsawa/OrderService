package com.ftgo.OrderService.domain.order.entity;

import javax.persistence.Embeddable;

/**
 * Item of order storing in the database.
 */

@Embeddable
public class OrderLineItemOnDB {
    private int quantity;
    private int menuItemId;
    private String name;
    private Long price;

    public OrderLineItemOnDB() {}

    public OrderLineItemOnDB(int quantity, int menuItemId, String name, Long price) {
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
    }
}
