package com.ftgo.OrderService.domain.order.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressOnDB {
    @Column(name = "street1")
    private String street1;

    @Column(name = "street2")
    private String street2;

    @Column(name = "city")
    private String city;

    @Column(name = "address_state")
    private String state;

    @Column(name = "zip")
    private String zip;

    public AddressOnDB() {
    }

    public AddressOnDB(String street1, String street2, String city, String state, String zip) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}