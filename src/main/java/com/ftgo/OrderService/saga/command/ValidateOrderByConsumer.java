package com.ftgo.OrderService.saga.command;

import com.ftgo.OrderService.domain.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public class ValidateOrderByConsumer implements Command {

    private long consumerId;
    private long orderId;
    private Money orderTotal;

    public ValidateOrderByConsumer(long consumerId, long orderId, Money orderTotal) {
        this.consumerId = consumerId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }
}