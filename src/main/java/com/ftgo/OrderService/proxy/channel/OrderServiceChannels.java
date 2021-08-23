package com.ftgo.OrderService.proxy.channel;

/**
 * Channel of order service.
 * This is used by Eventuate Tram Framework.
 */

public class OrderServiceChannels {
    public static final String COMMAND_CHANNEL = "orderService";
    public static final String ORDER_EVENT_CHANNEL = "com.ftgo.domain.order.Order";
}
