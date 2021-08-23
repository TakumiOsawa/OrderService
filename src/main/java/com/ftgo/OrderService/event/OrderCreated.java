package com.ftgo.OrderService.event;

import com.ftgo.OrderService.domain.order.OrderDetails;

/**
 * Event to signify order created.
 */

public class OrderCreated implements OrderDomainEvent {
    private final OrderDetails orderDetails;

    public OrderCreated(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
