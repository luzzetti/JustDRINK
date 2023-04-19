package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.ErrorCode;

public enum RestaurantErrors implements ErrorCode {
  NAME_REQUIRED("restaurant.name.required"),
  ADDRESS_REQUIRED("restaurant.address.required"),
  ID_REQUIRED("restaurant.id.required"),
  CUISINE_NOT_EXISTING("restaurant.cuisine.not.contained"),
  CUISINE_ALREADY_EXISTING("restaurant.cuisine.already.contained");

  private final String code;

  RestaurantErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
