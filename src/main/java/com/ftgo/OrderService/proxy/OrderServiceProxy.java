package com.ftgo.OrderService.proxy;

import com.ftgo.OrderService.proxy.channel.OrderServiceChannels;
import com.ftgo.OrderService.proxy.command.ApproveOrderCommand;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of OrderService.
 */

public class OrderServiceProxy {
    public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
            .forCommand(ApproveOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
