package com.ftgo.OrderService.domain;

import lombok.Getter;

/**
 * Value object to signify money.
 */

public class Money {
    @Getter
    private final long value;

    private Money(long value) {
        this.value = value;
    }

    public static Money create(long value) {
        return new Money(value);
    }

    public Money add(Money money) {
        return Money.create(value + money.value);
    }

    public Money mul(int num) {
        return Money.create(num * value);
    }
}
