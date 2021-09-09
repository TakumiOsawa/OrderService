package com.ftgo.OrderService.domain;

import com.ftgo.OrderService.domain.order.entity.MoneyOnDB;
import lombok.Getter;

/**
 * Value object to signify money.
 */

public class Money {
    @Getter
    private final long value;

    /**
     * Constructor
     * @param value value
     */
    private Money(long value) {
        this.value = value;
    }

    /**
     * Create Money.
     * @param value value
     * @return instance of Money.
     */
    public static Money create(long value) {
        return new Money(value);
    }

    /**
     * Create Money by adding the values of two Money.
     * @param money adding money.
     * @return new Money by adding the values of two Money.
     */
    public Money add(Money money) {
        return Money.create(value + money.value);
    }

    /**
     * Create Money by multiplying the scalar value.
     * @param num multiple value.
     * @return new Money by multiplying the scalar value.
     */
    public Money mul(int num) {
        return Money.create(num * value);
    }

    public MoneyOnDB transformEmbeddable() {
        return new MoneyOnDB(value);
    }
}
