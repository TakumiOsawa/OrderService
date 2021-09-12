package com.ftgo.OrderService.saga.proxy;

import com.ftgo.OrderService.saga.channel.ConsumerServiceChannels;
import com.ftgo.OrderService.saga.command.ValidateOrderByConsumer;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;

/**
 * Proxy of ConsumerService.
 */

public class ConsumerServiceProxy {
    public final CommandEndpoint<ValidateOrderByConsumer> validateOrder = CommandEndpointBuilder
            .forCommand(ValidateOrderByConsumer.class)
            .withChannel(ConsumerServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
