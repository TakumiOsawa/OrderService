package com.ftgo.OrderService.event;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.event.query.QueryPublished;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderEventConsumer {
    private final OrderService orderService;

    public OrderEventConsumer(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType("Order")
                .onEvent(QueryPublished.class, this::handleQueryPublished)
                .build();
    }

    public void handleQueryPublished(DomainEventEnvelope<QueryPublished> event) {
        //orderService.searchOrder(Long.parseLong(event.getAggregateId()));
    }
}
