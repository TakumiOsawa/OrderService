package com.ftgo.OrderService.domain.order.entity;

import com.ftgo.OrderService.domain.Money;
import lombok.Getter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter
public class OrderLineItemsOnDB {
    @ElementCollection
    @CollectionTable(name = "order_line_items")
    private List<OrderLineItemOnDB> value;

    public OrderLineItemsOnDB() {}

    public OrderLineItemsOnDB(List<OrderLineItemOnDB> value) {
        this.value = value;
    }
}
