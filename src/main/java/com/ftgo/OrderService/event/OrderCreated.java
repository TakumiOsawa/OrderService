package com.ftgo.OrderService.event;

/**
 * Order created event.
 */

public class OrderCreated implements OrderDomainEvent {
    private List<OrderLineItem> lineItems;
    private DeliveryInformation deliveryInformation;
    private PaymentInformation paymentInformation;
    private long restaurantId;
    private String restaurantName;
}
