package it.luzzetti.justdrink.order.domain.aggregates.order;

import it.luzzetti.commons.exceptions.ElementNotValidException;
import java.math.BigDecimal;

public record Price(BigDecimal price) {
  public static Price of(BigDecimal price) {
    return new Price(price);
  }

  public Price {
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new ElementNotValidException(OrderError.NEGATIVE_PRICE);
    }
  }
}
