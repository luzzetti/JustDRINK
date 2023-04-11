package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
  @Builder.Default private ProductId id = ProductId.empty();
  private String name;
  private BigDecimal price;

  @Builder.Default private Instant createdAt = Instant.now();

  public static Product newProduct(String name, BigDecimal price) {
    return Product.builder().name(name).price(price).build();
  }
}
