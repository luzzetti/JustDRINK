package it.luzzetti.justdrink.order.domain.aggregates.order;

import it.luzzetti.justdrink.order.domain.shared.typed_ids.ProductId;

public class OrderedProduct {
  private ProductId productId;
  private Price price;
}
