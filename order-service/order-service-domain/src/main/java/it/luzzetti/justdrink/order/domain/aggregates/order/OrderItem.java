package it.luzzetti.justdrink.order.domain.aggregates.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem {
  private OrderedProduct orderedProduct;
  private OrderQuantity quantity;
}
