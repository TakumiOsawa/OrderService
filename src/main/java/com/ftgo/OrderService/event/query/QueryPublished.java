package com.ftgo.OrderService.event.query;

import io.eventuate.tram.events.common.DomainEvent;
import lombok.Data;

@Data
public class QueryPublished implements DomainEvent {
    private long consumerId;

    public QueryPublished(long consumerId) {
        this.consumerId = consumerId;
    }
}
