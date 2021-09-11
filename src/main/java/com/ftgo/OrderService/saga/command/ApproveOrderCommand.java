package com.ftgo.OrderService.saga.command;

/**
 * Command to signify approving order.
 */

public class ApproveOrderCommand extends OrderCommand {
    public ApproveOrderCommand(long orderId) {
        super(orderId);
    }
}