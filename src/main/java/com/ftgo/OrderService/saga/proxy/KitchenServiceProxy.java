package com.ftgo.OrderService.saga.proxy;

import com.ftgo.OrderService.saga.CreateTicketReply;
import com.ftgo.OrderService.saga.channel.KitchenServiceChannels;
import com.ftgo.OrderService.saga.command.CancelCreateTicket;
import com.ftgo.OrderService.saga.command.ConfirmCreateTicket;
import com.ftgo.OrderService.saga.command.CreateTicket;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of KitchenService.
 */

public class KitchenServiceProxy {
    public final CommandEndpoint<CreateTicket> create = CommandEndpointBuilder
            .forCommand(CreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANNEL)
            .withReply(CreateTicketReply.class)
            .build();

    public final CommandEndpoint<ConfirmCreateTicket> confirmCreate = CommandEndpointBuilder
            .forCommand(ConfirmCreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<CancelCreateTicket> cancel = CommandEndpointBuilder
            .forCommand(CancelCreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
