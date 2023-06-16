package it.luzzetti.justdrink.geocodifica.domain.aggregates.address;

import it.luzzetti.commons.exceptions.ErrorCode;

public enum AddressErrors implements ErrorCode {
  IMPOSSIBLE_TO_GEOCODE("domain.restaurant.address.geocoding.impossible"),
  IMPOSSIBLE_TO_DETERMINATE("domain.restaurant.address.geocoding.not_unique_result");

  private final String code;

  AddressErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
