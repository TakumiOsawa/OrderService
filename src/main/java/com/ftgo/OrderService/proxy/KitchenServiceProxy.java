package com.ftgo.OrderService.proxy;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of KitchenService.
 */

public class KitchenServiceProxy {
    /*
    public final CommandEndpoint<CreateTicket> create = CommandEndpointBuilder
            .forCommand(CreateTicket.class)
            .withChannel(KitchenServiceChannels.kitchenServiceChannels)
            .withReply(CreateTicketReply.class)
            .build();

    public final CommandEndpoint<ConfirmCreateTicket> confirmCreate = CommandEndpointBuilder
            .forCommand(ConfirmCreateTicket.class)
            .withChannel(KitchenServiceChannels.kitchenServiceChannels)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<CancelCreateTicket> cancel = CommandEndpointBuilder
            .forCommand(CancelCreateTicket.class)
            .withChannel(KitchenServiceChannels.kitchenServiceChannels)
            .withReply(Success.class)
            .build();

     */
}
