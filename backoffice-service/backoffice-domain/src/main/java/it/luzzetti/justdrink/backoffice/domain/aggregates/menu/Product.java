package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;

@Getter
public class Product {
  private ProductId id = ProductId.empty();
  private String name;
  private BigDecimal price;

  private Instant createdAt = Instant.now();

  public static Product newProduct(String name, BigDecimal price) {
    return new Product(name, price);
  }

  private Product(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }
}
