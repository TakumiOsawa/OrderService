package com.ftgo.OrderService.domain.order.entity;

import com.ftgo.OrderService.domain.order.OrderDetails;
import com.ftgo.OrderService.domain.order.OrderLineItems;
import com.ftgo.OrderService.domain.order.OrderState;
import com.ftgo.OrderService.event.OrderAuthorized;
import com.ftgo.OrderService.event.OrderCreated;
import com.ftgo.OrderService.exception.UnsupportedStateTransitionException;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.ftgo.OrderService.domain.order.OrderState.APPROVED;

/**
 * Aggregate of order.
 */

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Version
    private Long version;

    private String state;
    private Long consumerId;
    private Long restaurantId;

    @ElementCollection
    @CollectionTable(name = "order_line_items")
    private List<OrderLineItemOnDB> orderLineItems;

    /*
    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;
    */

    public Order() {}

    public Order(Long consumerId, Long restaurantId, OrderLineItems orderLineItems) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems.transformEmbeddable();
        state = OrderState.APPROVAL_PENDING.name();
    }

    public static ResultWithEvents<Order> createOrder(
            long consumerId, long restaurantId, OrderLineItems orderLineItems) {
        Order order = new Order(consumerId, restaurantId, orderLineItems);
        List<DomainEvent> events = Collections.singletonList(
                new OrderCreated(
                        OrderDetails.create(consumerId, restaurantId, orderLineItems)));
        return new ResultWithEvents<>(order, events);
    }

    public List<DomainEvent> noteApproved() {
        switch (state) {
            case "APPROVAL_PENDING":
                state = APPROVED.name();
                return Collections.singletonList(new OrderAuthorized());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
/*
    public List<DomainEvent> noteRejected() {
        switch (state) {
            case APPROVAL_PENDING:
                state = REJECTED;
                return Collections.singletonList(new OrderRejected());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }

    public List<OrderDomainEvent> revise(OrderRivision orderRivision) {
        switch (state) {
            case APPROVED:
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRivision);
                if (change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)) {
                    throw new OrderMinimumNotMetException();
                }
                state = REVISION_PENDING;
                return Collections.singletonList(
                        new OrderRevisionProposed(
                                orderRivision, change.currentOrderTotal, change.newOrderTotal));
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }

    public List<OrderDomainEvent> confirmRevision(OrderRivision orderRivision) {
        switch (state) {
            case REVISION_PENDING:
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRivision);
                orderRivision.getDeliveryInformation()
                        .ifPresent(newDi -> deliveryInformation = newDi);
                if(!orderRivision.getRevisedLineItemQuantities().isEmpty()) {
                    orderLineItems.updateLineItems(orderRivision);
                }
                state = APPROVED;
                return Collections.singletonList(
                        new OrderRevised(orderRivision, change.currentOrderTotal, change.newOrderTotal));
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    */
}
