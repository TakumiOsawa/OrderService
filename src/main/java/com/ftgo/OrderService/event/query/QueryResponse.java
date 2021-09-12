package com.ftgo.OrderService.event.query;

import com.ftgo.OrderService.domain.order.entity.OrderLineItemOnDB;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryResponse implements DomainEvent {
    private Long orderId;
    private String state;
    private Long version;
    private Long consumerId;
    private Long restaurantId;
    private List<OrderLineItemOnDB> orderLineItems;
}
