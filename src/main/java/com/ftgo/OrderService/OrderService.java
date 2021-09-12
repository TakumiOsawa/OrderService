package com.ftgo.OrderService;

import com.ftgo.OrderService.domain.order.OrderDetails;
import com.ftgo.OrderService.domain.order.OrderLineItems;
import com.ftgo.OrderService.domain.order.entity.DeliveryInformation;
import com.ftgo.OrderService.domain.order.entity.Order;
import com.ftgo.OrderService.domain.order.repository.*;
import com.ftgo.OrderService.event.OrderDomainEventPublisher;
import com.ftgo.OrderService.exception.OrderNotFoundException;
import com.ftgo.OrderService.saga.CreateOrderSaga;
import com.ftgo.OrderService.saga.CreateOrderSagaState;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

/**
 * Service to manage orders.
 */

@Transactional
public class OrderService {
    private final SagaInstanceFactory sagaInstanceFactory;
    private final OrderRepository orderRepository;
    private final OrderDomainEventPublisher eventPublisher;
    private final CreateOrderSaga createOrderSaga;

    public OrderService(@Autowired SagaInstanceFactory sagaInstanceFactory,
                        @Autowired OrderRepository orderRepository,
                        @Autowired OrderDomainEventPublisher eventPublisher,
                        @Autowired CreateOrderSaga createOrderSaga) {
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.createOrderSaga = createOrderSaga;
    }

    public Order createOrder(Long restaurantId, Long consumerId, DeliveryInformation deliveryInformation,
                             OrderLineItems orderLineItems) {
        ResultWithEvents<Order> orderAndEvents =
                Order.createOrder(consumerId, restaurantId, deliveryInformation, orderLineItems);
        Order order = orderAndEvents.result;
        orderRepository.save(order);
        eventPublisher.publish(order, orderAndEvents.events);

        OrderDetails details =
                OrderDetails.create(consumerId, restaurantId, orderLineItems,
                        orderLineItems.getOrderTotal());
        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), details);
        sagaInstanceFactory.create(createOrderSaga, data);
        return order;
    }

    private void updateOrder(long orderId, Function<Order, List<DomainEvent>> updater) {
        orderRepository.findById(orderId)
                .map(order -> {
                    eventPublisher.publish(order, updater.apply(order));
                    orderRepository.save(order);
                    return order;
                }).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public void approveOrder(long orderId) {
        updateOrder(orderId, Order::noteApproved);
    }

    public void rejectOrder(long orderId) { updateOrder(orderId, Order::noteRejected); }
}
