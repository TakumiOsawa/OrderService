package com.ftgo.OrderService.saga;

import com.ftgo.OrderService.domain.order.OrderDetails;
import com.ftgo.OrderService.domain.order.OrderLineItem;
import com.ftgo.OrderService.domain.ticket.TicketDetails;
import com.ftgo.OrderService.domain.ticket.TicketLineItem;
import com.ftgo.OrderService.saga.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * State of CreateOrderSaga.
 */

public class CreateOrderSagaState {
    private static final Logger logger = LoggerFactory.getLogger("com.ftgo");

    private final Long orderId;
    private final OrderDetails orderDetails;
    private long ticketId;

    public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    CreateTicket makeCreateTicketCommand() {
        return new CreateTicket(orderDetails.getRestaurantId(), orderId,
                makeTicketDetails(orderDetails));
    }

    private TicketDetails makeTicketDetails(OrderDetails orderDetails) {
        return TicketDetails.create(orderId,
                makeTicketLineItems(orderDetails.getOrderLineItems().getValue()));
    }

    private List<TicketLineItem> makeTicketLineItems(List<OrderLineItem> lineItems) {
        return lineItems.stream()
                .map(this::makeTicketLineItem)
                .collect(Collectors.toList());
    }

    private TicketLineItem makeTicketLineItem(OrderLineItem orderLineItem) {
        return TicketLineItem.create(orderLineItem.getMenuItemId(),
                orderLineItem.getName(), orderLineItem.getQuantity());
    }

    void handleCreateTicketReply(CreateTicketReply reply) {
        logger.debug("getTicketId {}", reply.getTicketId());
        ticketId = reply.getTicketId();
    }

    CancelCreateTicket makeCancelCreateTicketCommand() {
        return new CancelCreateTicket(orderId);
    }

    RejectOrderCommand makeRejectOrderCommand() {
        return new RejectOrderCommand(orderId);
    }

    ValidateOrderByConsumer makeValidateOrderByConsumerCommand() {
        return new ValidateOrderByConsumer(orderDetails.getConsumerId(), orderId, orderDetails.getOrderTotal());
    }

    AuthorizeCommand makeAuthorizeCommand() {
        return new AuthorizeCommand(orderDetails.getConsumerId(), orderId, orderDetails.getOrderTotal());
    }

    ApproveOrderCommand makeApproveOrderCommand() {
        return new ApproveOrderCommand(orderId);
    }

    ConfirmCreateTicket makeConfirmCreateTicketCommand() {
        return new ConfirmCreateTicket(ticketId);
    }
}
