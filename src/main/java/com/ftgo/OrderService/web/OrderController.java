package com.ftgo.OrderService.web;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.domain.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/hcheck")
    public HealthCheckResponse healthCheck() {
        return new HealthCheckResponse();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CreateOrderResponse create(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(
                request.getConsumerId(),
                request.getRestaurantId(),
                null
                //request.getLineItems().stream().map(x -> new MenuItemIdAndQuantity(x.getMenuItemId(), x.getQuantity())).collect(toList())
        );
        return new CreateOrderResponse(order.getId());
    }
}