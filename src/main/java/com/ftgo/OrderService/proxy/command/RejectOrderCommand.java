package com.ftgo.OrderService.proxy.command;

public class RejectOrderCommand  extends OrderCommand {
    public RejectOrderCommand(long orderId) {
        super(orderId);
    }
}