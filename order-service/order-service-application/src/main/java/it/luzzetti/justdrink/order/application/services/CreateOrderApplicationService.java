package it.luzzetti.justdrink.order.application.services;

import it.luzzetti.justdrink.order.application.ports.input.CreateOrderUseCase;
import it.luzzetti.justdrink.order.application.ports.output.OrderPersistencePort;
import it.luzzetti.justdrink.order.domain.aggregates.order.Order;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.RestaurantId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreateOrderApplicationService implements CreateOrderUseCase {

  private final OrderPersistencePort orderPersistencePort;

  @Override
  @Transactional
  public void createOrder(CreateOrderCommand command) {

    var theOrderId = OrderId.from(UUID.nameUUIDFromBytes("TEST_ORDER_ID".getBytes()));
    var theCustomerId = CustomerId.from(UUID.nameUUIDFromBytes("TEST_CUSTOMER_ID".getBytes()));
    var theRestaurantId =
        RestaurantId.from(UUID.nameUUIDFromBytes("TEST_RESTAURANT_ID".getBytes()));

    var aNewOrder =
        Order.builder()
            .orderId(theOrderId)
            .customerId(theCustomerId)
            .restaurantId(theRestaurantId)
            .build();

    orderPersistencePort.saveOrder(aNewOrder);
  }
}
