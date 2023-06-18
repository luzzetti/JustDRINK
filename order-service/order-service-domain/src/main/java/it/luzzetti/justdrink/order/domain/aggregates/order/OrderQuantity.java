package it.luzzetti.justdrink.order.domain.aggregates.order;

import it.luzzetti.commons.exceptions.ElementNotValidException;

/*
 * TODO: il 15 è un numero arbitrario di oggetti massimi dello stesso tipo acquistabili.
 * Ovviamente, dovrebbe essere messo nelle properties, ed iniettato
 */
public record OrderQuantity(Integer quantity) {
  public static final int MAX_QUANTITY_ALLOWED_PER_ITEM = 15;

  public static OrderQuantity of(Integer quantity) {
    return new OrderQuantity(quantity);
  }

  public OrderQuantity {
    if (quantity <= 0) {
      throw new ElementNotValidException(OrderError.NEGATIVE_QUANTITY);
    }
    if (quantity >= MAX_QUANTITY_ALLOWED_PER_ITEM) {
      throw new ElementNotValidException(OrderError.TOO_MANY_ITEMS);
    }
  }
}
