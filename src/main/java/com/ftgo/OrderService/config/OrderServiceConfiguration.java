package com.ftgo.OrderService.config;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.domain.order.repository.OrderRepository;
import com.ftgo.OrderService.event.OrderDomainEventPublisher;
import com.ftgo.OrderService.proxy.AccountingServiceProxy;
import com.ftgo.OrderService.proxy.ConsumerServiceProxy;
import com.ftgo.OrderService.proxy.OrderServiceProxy;
import com.ftgo.OrderService.saga.CreateOrderSaga;
import com.ftgo.OrderService.proxy.KitchenServiceProxy;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration of OrderService.
 */

@Configuration
@Import({TramEventsPublisherConfiguration.class,
        SagaOrchestratorConfiguration.class,
        TramMessageProducerJdbcConfiguration.class})
public class OrderServiceConfiguration {
    @Bean
    public OrderService orderService(SagaInstanceFactory sagaInstanceFactory,
                                     OrderRepository orderRepository,
                                     OrderDomainEventPublisher eventPublisher,
                                     CreateOrderSaga createOrderSaga) {
        return new OrderService(sagaInstanceFactory, orderRepository, eventPublisher, createOrderSaga);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService,
                                           ConsumerServiceProxy consumerService,
                                           KitchenServiceProxy kitchenService,
                                           AccountingServiceProxy accountingService) {
        return new CreateOrderSaga(orderService, consumerService, kitchenService, accountingService);
    }

    @Bean
    public OrderDomainEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
        return new OrderDomainEventPublisher(eventPublisher);
    }

    @Bean
    public KitchenServiceProxy kitchenServiceProxy() {
        return new KitchenServiceProxy();
    }

    @Bean
    public OrderServiceProxy orderServiceProxy() {
        return new OrderServiceProxy();
    }

    @Bean
    public ConsumerServiceProxy consumerServiceProxy() { return new ConsumerServiceProxy(); }

    @Bean
    public AccountingServiceProxy accountingServiceProxy() { return new AccountingServiceProxy(); }
}
