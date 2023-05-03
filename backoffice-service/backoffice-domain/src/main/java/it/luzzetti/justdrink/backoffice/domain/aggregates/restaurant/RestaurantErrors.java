package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ErrorCode;

public enum RestaurantErrors implements ErrorCode {
  NOT_FOUND("domain.restaurant.not_found"),
  NOT_IMPLEMENTED_FEATURE("domain.restaurant.feature_not_implemented"),
  NAME_REQUIRED("domain.restaurant.name.required"),
  ADDRESS_REQUIRED("domain.restaurant.address.required"),
  ID_REQUIRED("domain.restaurant.id.required"),
  CUISINE_NOT_EXISTING("domain.restaurant.cuisine.not.contained"),
  CUISINE_ALREADY_EXISTING("domain.restaurant.cuisine.already.contained"),
  IMPOSSIBLE_TO_GEOCODE("domain.restaurant.address.geocoding.impossible"),

  IMPOSSIBLE_UPLOAD_LOGO("domain.restaurant.logo.impossible.upload"),
  IMPOSSIBLE_DOWNLAOD_LOGO("domain.restaurant.logo.impossible.download"),

  PATH_REQUIRED("domain.restaurant.logo.path.required");

  private final String code;

  RestaurantErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
