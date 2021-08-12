package com.ftgo.OrderService.config;

import com.ftgo.OrderService.OrderService;
import com.ftgo.OrderService.saga.CreateOrderSaga;
import com.ftgo.OrderService.saga.CreateOrderSagaState;
import com.ftgo.OrderService.saga.OrderCommandHandlers;
import com.ftgo.OrderService.saga.proxy.KitchenServiceProxy;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import io.eventuate.tram.sagas.orchestration.SagaManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of OrderService.
 */

@Configuration
public class OrderServiceConfiguration {
    @Bean
    public OrderService orderService(RestaurantRepository restaurantRepository,
                                     SagaManager<CreateOrderSagaState> createOrderSagaManager) {
        return new OrderService(restaurantRepository, createOrderSagaManager);
    }

    @Bean
    public SagaManager<CreateOrderSagaState> createOrderSagaManager(CreateOrderSaga saga) {
        return new SagaManagerImpl<>(saga);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService,
                                           ConsumerServiceProxy consumerService) {
        return new CreateOrderSaga(orderService, consumerService);
    }

    @Bean
    public OrderCommandHandlers orderCommandHandlersDispatcher(OrderCommandHandlers orderCommandHandlers) {
        return new CommandDispatcher("orderService", orderCommandHandlers.commandHandlers());
    }

    @Bean
    public KitchenServiceProxy kitchenServiceProxy() {
        return new KitchenServiceProxy();
    }

    @Bean
    public OrderServiceProxy orderServiceProxy() {
        return new OrderServiceProxy();
    }
}
