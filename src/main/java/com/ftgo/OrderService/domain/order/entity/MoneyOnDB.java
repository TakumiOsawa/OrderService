package com.ftgo.OrderService.domain.order.entity;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class MoneyOnDB {
    @Column(name = "price")
    private long value;

    public MoneyOnDB() {}

    /**
     * Constructor
     * @param value value
     */
    public MoneyOnDB(long value) {
        this.value = value;
    }
}