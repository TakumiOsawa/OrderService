package com.ftgo.OrderService;

import com.ftgo.OrderService.saga.CreateOrderSagaState;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service to manage orders.
 */

public class OrderService {
    private final SagaManager<CreateOrderSagaState> createOrderSagaManager;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    public OrderService(@Autowired SagaManager<CreateOrderSagaState> createOrderSagaManager,
                        @Autowired OrderRepository orderRepository,
                        @Autowired DomainEventPublisher eventPublisher) {
        this.createOrderSagaManager = createOrderSagaManager;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }


    public Order createOrder(OrderDetails orderDetails) {
        ResultWithEvents<Order> orderAndEvents = Order.createOrder();
        Order order = orderAndEvents.result;
        orderRepository.save(order);

        eventPublisher.publish(Order.class, Long.toString(order.getId()), orderAndEvents.events);

        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        createOrderSagaManager.create(data, Order.class, order.getId());

        return order;
    }
}
