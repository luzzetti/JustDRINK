package it.luzzetti.justdrink.order.application.ports.output;

import it.luzzetti.justdrink.order.domain.aggregates.order.Order;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import java.util.Optional;

public interface OrderPersistencePort {

  Optional<Order> findOrderById(OrderId orderId);

  Order findOrderByIdMandatory(OrderId orderId);
  void saveOrder(Order theOrder);
}
