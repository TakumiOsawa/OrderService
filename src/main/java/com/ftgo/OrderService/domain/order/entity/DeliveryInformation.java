package com.ftgo.OrderService.domain.order.entity;

import com.ftgo.OrderService.domain.Address;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Embeddable
@Access(AccessType.FIELD)
public class DeliveryInformation {
    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Embedded
    private AddressOnDB deliveryAddress;

    public DeliveryInformation() {
    }

    public DeliveryInformation(LocalDateTime deliveryTime, Address deliveryAddress) {
        this.deliveryTime = deliveryTime;
        this.deliveryAddress = deliveryAddress.transformEmbeddable();
    }
}