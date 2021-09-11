package com.ftgo.OrderService.domain.order.entity;

import com.ftgo.OrderService.domain.order.*;
import com.ftgo.OrderService.event.OrderAuthorized;
import com.ftgo.OrderService.event.OrderCreated;
import com.ftgo.OrderService.event.OrderRejected;
import com.ftgo.OrderService.exception.UnsupportedStateTransitionException;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.ftgo.OrderService.domain.order.OrderState.*;

/**
 * Aggregate of order.
 */

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {
    @Id
    @Getter
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "consumer_id")
    private Long consumerId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Embedded
    private OrderLineItemsOnDB orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;

    public Order() {}

    public Order(Long consumerId, Long restaurantId, DeliveryInformation deliveryInformation,
                 OrderLineItems orderLineItems) {
        id = Math.abs(new Random().nextLong());
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.deliveryInformation = deliveryInformation;
        this.orderLineItems = orderLineItems.transformEmbeddable();
        state = OrderState.APPROVAL_PENDING;
    }

    public static ResultWithEvents<Order> createOrder(
            long consumerId, long restaurantId, DeliveryInformation deliveryInformation,
            OrderLineItems orderLineItems) {
        Order order = new Order(consumerId, restaurantId, deliveryInformation, orderLineItems);
        List<DomainEvent> events = Collections.singletonList(
                new OrderCreated(
                        OrderDetails.create(consumerId, restaurantId, orderLineItems)));
        return new ResultWithEvents<>(order, events);
    }

    public List<DomainEvent> noteApproved() {
        if(state == APPROVAL_PENDING) {
            state = APPROVED;
            return Collections.singletonList(new OrderAuthorized());
        }
        else{
            throw new UnsupportedStateTransitionException(state);
        }
    }

    public List<DomainEvent> noteRejected() {
        if(state == APPROVAL_PENDING) {
            state = REJECTED;
            return Collections.singletonList(new OrderRejected());
        } else {
           throw new UnsupportedStateTransitionException(state);
        }
    }
}
