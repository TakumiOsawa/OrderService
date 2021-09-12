package com.ftgo.OrderService.web;

import com.ftgo.OrderService.domain.Address;
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
    private Address deliveryAddress;
    private List<LineItem> lineItems;

    public CreateOrderRequest(long consumerId, long restaurantId, LocalDateTime deliveryTime,
                              List<LineItem> lineItems, Address deliveryAddress) {
        this.restaurantId = restaurantId;
        this.consumerId = consumerId;
        this.deliveryTime = deliveryTime;
        this.lineItems = lineItems;
        this.deliveryAddress = deliveryAddress;
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
