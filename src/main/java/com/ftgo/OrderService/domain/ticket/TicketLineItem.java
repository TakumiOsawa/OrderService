package com.ftgo.OrderService.domain.ticket;

import lombok.Getter;

@Getter
public class TicketLineItem {
    private int quantity;
    private String menuItemId;
    private String name;

    private TicketLineItem(String menuItemId, String name, int quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.quantity = quantity;
    }

    public static TicketLineItem create(String menuItemId, String name, int quantity){
        return new TicketLineItem(menuItemId, name, quantity);
    }
}