package com.ftgo.OrderService.domain.order;

import com.ftgo.OrderService.domain.Money;
import com.ftgo.OrderService.domain.order.entity.OrderLineItemsOnDB;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * First class collection of OrderLineItem.
 */

@Getter
public class OrderLineItems {
    private final List<OrderLineItem> value = new ArrayList<>();

    private OrderLineItems() {}

    public static OrderLineItems create() {
        return new OrderLineItems();
    }

    public void add(OrderLineItem item) {
        value.add(item);
    }

    public OrderLineItemsOnDB transformEmbeddable() {
        return new OrderLineItemsOnDB(
                value.stream()
                        .map(OrderLineItem::transformEmbeddable)
                        .collect(Collectors.toList()));
    }

    public Money getOrderTotal() {
        Money total = Money.create(0);
        return value.stream()
                .map(OrderLineItem::getOrderTotal)
                .reduce(total, Money::add);
    }
}
