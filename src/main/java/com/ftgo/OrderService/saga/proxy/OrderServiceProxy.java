package com.ftgo.OrderService.saga.proxy;

import com.ftgo.OrderService.saga.channel.OrderServiceChannels;
import com.ftgo.OrderService.saga.command.ApproveOrderCommand;
import com.ftgo.OrderService.saga.command.RejectOrderCommand;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of OrderService.
 */

public class OrderServiceProxy {
    public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder
            .forCommand(RejectOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
            .forCommand(ApproveOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
