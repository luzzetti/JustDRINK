package it.luzzetti.justdrink.order.domain.aggregates.order;

import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.RestaurantId;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order {
  private final OrderId orderId;
  private final CustomerId customerId;
  private final RestaurantId restaurantId;
  @Builder.Default private final List<OrderItem> lineItems = new ArrayList<>();
}
