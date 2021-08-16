package com.ftgo.OrderService.event;

/**
 * Saving event and meta data.
 * @param <T> Type of event
 */

public class DomainEventEnvelope<T> {
    private String aggregateType;
    private Object aggregateId;
    private T event;
}
