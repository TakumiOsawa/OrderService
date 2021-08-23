package com.ftgo.OrderService.domain.order.repository;

import com.ftgo.OrderService.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of order aggregate.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
}
