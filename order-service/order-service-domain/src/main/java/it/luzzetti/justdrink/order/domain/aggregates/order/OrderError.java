package it.luzzetti.justdrink.order.domain.aggregates.order;

import it.luzzetti.commons.exceptions.ErrorCode;

public enum OrderError implements ErrorCode {
  NEGATIVE_QUANTITY("domain.order.product.quantity.negative"),
  TOO_MANY_ITEMS("domain.order.product.quantity.too_much"),
  NEGATIVE_PRICE("domain.order.product.price.negative");

  private final String code;

  OrderError(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
