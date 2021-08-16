package com.ftgo.OrderService.order;

import com.ftgo.OrderService.event.OrderCreated;
import com.ftgo.OrderService.event.OrderDomainEvent;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

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

    private OrderState state;
    private Long consumerId;
    private Long restaurantId;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;

    @Embedded
    private Money orderMinimum;

    public static ResultWithEvents<Order, OrderDomainEvent> createOrder(
            long consumerId, Restaurant restaurant, List<OrderLineItem> orderLineItems) {
        Order order = new Order(consumerId, restaurant.getId(), orderLineItems);
        List<OrderDomainEvent> events = Collections.singletonList(
                new OrderCreated(
                        new OrderDetails(consumerId, restaurant.getId(), orderLineItems, order.getOrderTotal())
                        , restaurant.getName()));
        return new ResultWithEvents<>(order, events);
    }

    public Order(OrderDetails details) {
        orderLineItems = new OrderLineItems(details.getLineItems());
        orderMinimum = details.getOrderMinimum();
        state = APPLOVAL_PENDING;
    }

    public List<DomainEvent> noteApproved() {
        switch (state) {
            case APPROVAL_PENDING:
                state = APPROVED;
                return Collections.singletonList(new OrderAuthorized());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }

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
}
