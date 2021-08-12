package com.ftgo.OrderService.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public CreateTicket makeCreateTicketCommand() {
        return new CreateTicket(orderDetails.getRestaurantId(), orderId,
                makeTicketDetails(orderDetails));
    }

    public void handleCreateTicketReply(CreateTicketReply reply) {
        logger.debug("getTicketId {}", reply.getTicketId());
        ticketId = reply.getTicketId();
    }

    public CancelCreateTicket makeCancelCreateTicketCommand() {
        return new CancelCreateTicket(orderId);
    }
}
