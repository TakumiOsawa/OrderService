package com.ftgo.OrderService.exception;

/**
 * Exception meaning order not found.
 */

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super(orderId.toString());
    }
}
