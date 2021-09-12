package com.ftgo.OrderService.domain.order.repository;

import com.ftgo.OrderService.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository of order aggregate.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE consumer_id = :consumerId", nativeQuery = true)
    List<Order> searchByConsumerId(@Param("consumerId") long consumerId);
}
