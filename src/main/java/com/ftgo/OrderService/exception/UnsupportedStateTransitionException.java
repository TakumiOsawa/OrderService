package com.ftgo.OrderService.exception;

import com.ftgo.OrderService.domain.order.OrderState;

/**
 * Exception meaning unsupported state.
 */

public class UnsupportedStateTransitionException extends RuntimeException {
    public UnsupportedStateTransitionException(OrderState state) {
        super(state.name());
    }
}