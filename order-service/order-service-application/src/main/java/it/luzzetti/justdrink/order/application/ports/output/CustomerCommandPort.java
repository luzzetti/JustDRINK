package it.luzzetti.justdrink.order.application.ports.output;

import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;

public interface CustomerCommandPort {
  void validateCustomer(OrderId theOrderId, CustomerId theCustomerId);
}
