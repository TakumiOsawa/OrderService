package com.ftgo.OrderService.event;

import com.ftgo.OrderService.order.Order;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

/**
 * Event Publisher specialized for a order aggregate.
 */

public class OrderDomainEventPublisher  extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {
    public OrderDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Order.class, Order::getId);
    }
}