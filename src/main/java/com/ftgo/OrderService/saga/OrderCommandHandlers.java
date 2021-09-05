package com.ftgo.OrderService.saga;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.proxy.command.ApproveOrderCommand;
import com.ftgo.OrderService.proxy.command.RejectOrderCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import org.springframework.beans.factory.annotation.Autowired;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

/**
 * Command Handler of OrderService.
 */

public class OrderCommandHandlers {
    private final OrderService orderService;

    public OrderCommandHandlers(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    public CommandHandlers commandHandlers() {
        return CommandHandlersBuilder
                .fromChannel("orderService")
                .onMessage(ApproveOrderCommand.class, this::approveOrder)
                .onMessage(RejectOrderCommand.class, this::rejectOrder)
                .build();
    }

    public Message approveOrder(CommandMessage<ApproveOrderCommand> commandMessage) {
        long orderId = commandMessage.getCommand().getOrderId();
        orderService.approveOrder(orderId);
        return withSuccess();
    }

    public Message rejectOrder(CommandMessage<RejectOrderCommand> commandMessage) {
        long orderId = commandMessage.getCommand().getOrderId();
        orderService.rejectOrder(orderId);
        return withSuccess();
    }
}
