package com.ftgo.OrderService.exception;

/**
 * Exception meaning restaurant not found.
 */

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId) {
        super(restaurantId.toString());
    }
}
