package com.ftgo.OrderService.domain.order;

import lombok.Getter;

/**
 * Details of order.
 */

public class OrderDetails {
    private final Long consumerId;
    @Getter
    private final Long restaurantId;
    @Getter
    private final OrderLineItems orderLineItems;

    private OrderDetails(Long consumerId, Long restaurantId, OrderLineItems orderLineItems) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
    }

    public static OrderDetails create(Long consumerId, Long restaurantId, OrderLineItems orderLineItems) {
        return new OrderDetails(consumerId, restaurantId, orderLineItems);
    }
}
