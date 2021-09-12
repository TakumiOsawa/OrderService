package com.ftgo.OrderService.web;

import com.ftgo.OrderService.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersResponse {
    private List<Order> orders;
}
