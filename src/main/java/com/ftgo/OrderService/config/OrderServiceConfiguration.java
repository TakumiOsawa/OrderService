package com.ftgo.OrderService.config;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.domain.order.repository.OrderRepository;
import com.ftgo.OrderService.event.OrderDomainEventPublisher;
import com.ftgo.OrderService.event.OrderEventConsumer;
import com.ftgo.OrderService.saga.proxy.AccountingServiceProxy;
import com.ftgo.OrderService.saga.proxy.ConsumerServiceProxy;
import com.ftgo.OrderService.saga.proxy.OrderServiceProxy;
import com.ftgo.OrderService.saga.CreateOrderSaga;
import com.ftgo.OrderService.saga.proxy.KitchenServiceProxy;
import com.ftgo.OrderService.saga.OrderCommandHandlers;
import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.common.NoopDuplicateMessageDetector;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
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
        TramMessageProducerJdbcConfiguration.class,
        EventuateTramKafkaMessageConsumerConfiguration.class,
        SagaParticipantConfiguration.class,
        TramEventSubscriberConfiguration.class})
public class OrderServiceConfiguration {
    /**
     * Create OrderService.
     * @param sagaInstanceFactory sagaInstanceFactory
     * @param orderRepository orderRepository
     * @param eventPublisher eventPublisher
     * @param createOrderSaga createOrderSaga
     * @return instance of OrderService.
     */
    @Bean
    public OrderService orderService(SagaInstanceFactory sagaInstanceFactory,
                                     OrderRepository orderRepository,
                                     DomainEventPublisher eventPublisher,
                                     CreateOrderSaga createOrderSaga) {
        return new OrderService(sagaInstanceFactory, orderRepository, eventPublisher, createOrderSaga);
    }

    /**
     * Create CreateOrderSaga.
     * @param orderService orderService
     * @param consumerService consumerService
     * @param kitchenService kitchenService
     * @param accountingService accountingService
     * @return instance of CreateOrderSaga.
     */
    @Bean
    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService,
                                           ConsumerServiceProxy consumerService,
                                           KitchenServiceProxy kitchenService,
                                           AccountingServiceProxy accountingService) {
        return new CreateOrderSaga(orderService, consumerService, kitchenService, accountingService);
    }

    /**
     * Create KitchenServiceProxy.
     * @return instance of KitchenServiceProxy.
     */
    @Bean
    public KitchenServiceProxy kitchenServiceProxy() {
        return new KitchenServiceProxy();
    }

    /**
     * Create OrderServiceProxy.
     * @return instance of OrderServiceProxy.
     */
    @Bean
    public OrderServiceProxy orderServiceProxy() {
        return new OrderServiceProxy();
    }

    /**
     * Create ConsumerServiceProxy.
     * @return instance of ConsumerServiceProxy.
     */
    @Bean
    public ConsumerServiceProxy consumerServiceProxy() { return new ConsumerServiceProxy(); }

    /**
     * Create AccountingServiceProxy.
     * @return instance of AccountingServiceProxy.
     */
    @Bean
    public AccountingServiceProxy accountingServiceProxy() { return new AccountingServiceProxy(); }

    /**
     * Create DuplicateMessageDetector.
     * @return instance of NoopDuplicateMessageDetector.
     */
    @Bean
    public DuplicateMessageDetector duplicateMessageDetector() {
        return new NoopDuplicateMessageDetector();
    }

    @Bean
    public OrderCommandHandlers orderCommandHandlers(OrderService orderService) {
        return new OrderCommandHandlers(orderService);
    }

    @Bean
    public SagaCommandDispatcher orderCommandHandlersDispatcher(OrderCommandHandlers orderCommandHandlers,
                                                                SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory.make("orderService",
                orderCommandHandlers.commandHandlers());
    }

    @Bean
    public OrderEventConsumer orderHistoryEventHandlers(OrderService orderService) {
        return new OrderEventConsumer(orderService);
    }

    @Bean
    public DomainEventDispatcher orderHistoryDomainEventDispatcher(
            OrderEventConsumer orderEventConsumer,
            DomainEventDispatcherFactory domainEventDispatcherFactory) {
        return domainEventDispatcherFactory.make("orderDomainEventDispatcher",
                orderEventConsumer.domainEventHandlers());
    }
}
