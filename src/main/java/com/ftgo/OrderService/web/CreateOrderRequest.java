package com.ftgo.OrderService.web;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Request of create order api.
 */

@Data
public class CreateOrderRequest {
    private long restaurantId;
    private long consumerId;
    private LocalDateTime deliveryTime;
    private List<LineItem> lineItems;
    //private Address deliveryAddress;

    public CreateOrderRequest(long consumerId, long restaurantId, LocalDateTime deliveryTime, List<LineItem> lineItems) {
        this.restaurantId = restaurantId;
        this.consumerId = consumerId;
        this.deliveryTime = deliveryTime;
        this.lineItems = lineItems;
    }

    @Data
    public static class LineItem {
        private String menuItemId;
        private int quantity;

        public LineItem(String menuItemId, int quantity) {
            this.menuItemId = menuItemId;
            this.quantity = quantity;
        }
    }
}
