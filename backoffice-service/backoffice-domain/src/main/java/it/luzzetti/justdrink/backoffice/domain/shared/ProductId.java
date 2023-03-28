package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record ProductId(UUID id) {
  public static ProductId empty() {
    return new ProductId(null);
  }

  public static ProductId from(UUID productId) {
    return new ProductId(productId);
  }

}
