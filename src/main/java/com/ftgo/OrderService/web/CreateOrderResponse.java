package com.ftgo.OrderService.web;

import lombok.Data;

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
