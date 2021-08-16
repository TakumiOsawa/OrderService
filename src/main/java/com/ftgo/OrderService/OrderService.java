package com.ftgo.OrderService;

import com.ftgo.OrderService.event.OrderDomainEvent;
import com.ftgo.OrderService.event.OrderDomainEventPublisher;
import com.ftgo.OrderService.order.Order;
import com.ftgo.OrderService.saga.CreateOrderSagaState;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to manage orders.
 */

@Transactional
public class OrderService {
    private final SagaManager<CreateOrderSagaState> createOrderSagaManager;
    private final SagaManager<ReviseOrderSagaState> reviseOrderSagaManager;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDomainEventPublisher eventPublisher;

    public OrderService(@Autowired SagaManager<CreateOrderSagaState> createOrderSagaManager,
                        @Autowired SagaManager<ReviseOrderSagaState> reviseOrderSagaManager,
                        @Autowired OrderRepository orderRepository,
                        @Autowired RestaurantRepository restaurantRepository,
                        @Autowired OrderDomainEventPublisher eventPublisher) {
        this.createOrderSagaManager = createOrderSagaManager;
        this.reviseOrderSagaManager = reviseOrderSagaManager;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(OrderDetails details) {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantID));
        List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems, restaurant);
        ResultWithEvents<Order> orderAndEvents = Order.createOrder(consumerId, restaurant, orderLineItems);
        Order order = orderAndEvents.result;
        orderRepository.save(order);

        eventPublisher.publish(order, orderAndEvents.events);
        OrderDetails details = new OrderDetails(
                consumerId, restaurantId, orderLineItems, order.getOrderTotal());
        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        createOrderSagaManager.create(data, Order.class, order.getId());

        return order;
    }

    public Order reviseOrder(Long orderId, OrderRevision orderRevision) {
        Order order = orderRevision.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        ReviseOrderSagaData sagaData = new ReviseOrderSagaData(
                order.getConsumerId(), orderId, null, orderRevision);
        reviseOrderSagaManager.create(sagaData);
        return order;
    }
}
