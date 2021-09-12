package com.ftgo.OrderService.saga.command;

import com.ftgo.OrderService.domain.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public class AuthorizeCommand  implements Command {
    private long consumerId;
    private Long orderId;
    private Money orderTotal;

    public AuthorizeCommand(long consumerId, Long orderId, Money orderTotal) {
        this.consumerId = consumerId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }
}