package com.ftgo.OrderService.domain.order.entity;

import com.ftgo.OrderService.domain.Money;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Item of order storing in the database.
 */

@Embeddable
public class OrderLineItemOnDB {
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "menu_item_id")
    private String menuItemId;

    @Column(name = "name")
    private String name;

    @Embedded
    private MoneyOnDB price;

    public OrderLineItemOnDB() {}

    public OrderLineItemOnDB(int quantity, String menuItemId, String name, MoneyOnDB price) {
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
    }
}
