package com.ftgo.OrderService.saga.proxy;

import com.ftgo.OrderService.saga.channel.AccountingServiceChannels;
import com.ftgo.OrderService.saga.command.AuthorizeCommand;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of Accounting Service.
 */

public class AccountingServiceProxy {
    public final CommandEndpoint<AuthorizeCommand> authorize = CommandEndpointBuilder
            .forCommand(AuthorizeCommand.class)
            .withChannel(AccountingServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
