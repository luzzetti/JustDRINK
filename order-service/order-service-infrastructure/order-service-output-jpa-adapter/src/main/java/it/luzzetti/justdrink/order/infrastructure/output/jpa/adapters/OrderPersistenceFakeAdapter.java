package it.luzzetti.justdrink.order.infrastructure.output.jpa.adapters;

import it.luzzetti.commons.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.order.application.ports.output.OrderPersistencePort;
import it.luzzetti.justdrink.order.domain.aggregates.order.Order;
import it.luzzetti.justdrink.order.domain.aggregates.order.OrderError;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import it.luzzetti.justdrink.order.infrastructure.output.jpa.mappers.OrderJpaMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class OrderPersistenceFakeAdapter implements OrderPersistencePort {

  private static final Map<OrderId, Order> database = new HashMap<>();

  private final OrderJpaMapper orderJpaMapper;

  @Override
  public Optional<Order> findOrderById(OrderId orderId) {
    return Optional.ofNullable(database.get(orderId));
  }

  @Override
  public Order findOrderByIdMandatory(OrderId orderId) {
    return Optional.ofNullable(database.get(orderId))
        .map(orderJpaMapper::deepCloneOrder)
        .orElseThrow(() -> new ElementNotFoundException(OrderError.ORDER_NOT_FOUND));
  }

  @Override
  public void saveOrder(Order anOrder) {
    Objects.requireNonNull(anOrder.getOrderId());
    Objects.requireNonNull(anOrder);

    database.put(anOrder.getOrderId(), anOrder);
  }
}
