package com.ftgo.OrderService.domain.order;

import com.ftgo.OrderService.domain.Money;
import com.ftgo.OrderService.domain.order.entity.OrderLineItemOnDB;
import lombok.Getter;

/**
 * Item of order.
 */

@Getter
public class OrderLineItem {
    private final int quantity;
    private final String menuItemId;
    private final String name;
    private final Money price;

    private OrderLineItem(int quantity, String menuItemId, String name, long price) {
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = Money.create(price);
    }

    public static OrderLineItem create(int quantity, String menuItemId, String name, int price) {
        return new OrderLineItem(quantity, menuItemId, name, price);
    }

    public Money getOrderTotal() {
        return price.mul(quantity);
    }

    public OrderLineItemOnDB transformEmbeddable() {
        return new OrderLineItemOnDB(quantity, menuItemId, name, price.transformEmbeddable());
    }
}
