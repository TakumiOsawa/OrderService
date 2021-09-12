package com.ftgo.OrderService.domain.order;

import com.ftgo.OrderService.domain.Money;
import lombok.Getter;

/**
 * Details of order.
 */

@Getter
public class OrderDetails {
    private final Long consumerId;
    private final Long restaurantId;
    private final OrderLineItems orderLineItems;
    private Money orderTotal;

    private OrderDetails(Long consumerId, Long restaurantId,
                         OrderLineItems orderLineItems, Money orderTotal) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.orderTotal = orderTotal;
    }

    public static OrderDetails create(Long consumerId, Long restaurantId,
                                      OrderLineItems orderLineItems, Money orderTotal) {
        return new OrderDetails(consumerId, restaurantId, orderLineItems, orderTotal);
    }


}
