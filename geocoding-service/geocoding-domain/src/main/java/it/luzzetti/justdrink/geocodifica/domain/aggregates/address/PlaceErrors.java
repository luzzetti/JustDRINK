package it.luzzetti.justdrink.geocodifica.domain.aggregates.address;

import it.luzzetti.commons.exceptions.ErrorCode;

public enum PlaceErrors implements ErrorCode {
  IMPOSSIBLE_TO_GEOCODE("domain.address.geocoding.impossible"),
  IMPOSSIBLE_TO_DETERMINATE("domain.address.geocoding.not_unique_result"),
  ADDRESS_REQUIRED("domain.place.required.address");

  private final String code;

  PlaceErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
