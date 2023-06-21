package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.commons.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
  private ProductId id;
  private String name;
  private BigDecimal price;

  @Builder.Default private Instant createdAt = Instant.now();

  // Lombok's Builder Override

  public static ProductBuilder builder() {
    return new Product.CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends ProductBuilder {

    /* Adding validations as part of build() method. */
    public Product build() {

      if (super.id == null || super.id.id() == null) {
        throw new ElementNotValidException(MenuErrors.PRODUCT_ID_REQUIRED);
      }

      return super.build();
    }
  }
}
