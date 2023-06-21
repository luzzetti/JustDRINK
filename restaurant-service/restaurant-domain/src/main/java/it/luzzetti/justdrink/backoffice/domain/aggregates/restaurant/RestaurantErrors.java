package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.commons.exceptions.ErrorCode;

public enum RestaurantErrors implements ErrorCode {
  NOT_FOUND("domain.restaurant.not_found"),
  NOT_IMPLEMENTED_FEATURE("domain.restaurant.feature_not_implemented"),
  NAME_REQUIRED("domain.restaurant.name.required"),
  ADDRESS_REQUIRED("domain.restaurant.address.required"),
  ID_REQUIRED("domain.restaurant.id.required"),
  CUISINE_NOT_EXISTING("domain.restaurant.cuisine.not.contained"),
  CUISINE_ALREADY_EXISTING("domain.restaurant.cuisine.already.contained"),
  IMPOSSIBLE_TO_GEOCODE("domain.address.geocoding.impossible"),
  PATH_REQUIRED("domain.restaurant.logo.path.required"),
  LOGO_WRONG_CONTENT_TYPE("rest.adapter.restaurant.logo.upload.wrong_content_type"),
  LOGO_UPLOAD_IMPOSSIBLE("domain.restaurant.logo.upload.impossible"),
  LOGO_DOWNLOAD_IMPOSSIBLE("domain.restaurant.logo.download.impossible"),
  OWNER_REQUIRED("domain.restaurant.owner.required");

  private final String code;

  RestaurantErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
