package com.ftgo.OrderService.web;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response of create order api.
 */

@Data
public class CreateOrderResponse {
    private long orderId;

    public CreateOrderResponse(long orderId) {
        this.orderId = orderId;
    }
}
