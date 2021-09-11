package com.ftgo.OrderService.saga.command;

public class RejectOrderCommand  extends OrderCommand {
    public RejectOrderCommand(long orderId) {
        super(orderId);
    }
}