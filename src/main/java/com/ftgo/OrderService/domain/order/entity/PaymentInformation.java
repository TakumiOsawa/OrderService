package com.ftgo.OrderService.domain.order.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class PaymentInformation {
    @Column(name = "payment_token")
    private String paymentToken;
}