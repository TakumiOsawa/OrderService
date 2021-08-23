package com.ftgo.OrderService.proxy.command;

import io.eventuate.tram.commands.common.Command;
import lombok.Data;

/**
 * Command is used by order service.
 */

@Data
public abstract class OrderCommand implements Command {
    private long orderId;

    protected OrderCommand(long orderId) {
        this.orderId = orderId;
    }
}