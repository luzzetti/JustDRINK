package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ErrorCode;

public enum MenuErrors implements ErrorCode {
  MENU_NOT_FOUND_ON_RESTAURANT("domain.menu.not_found.this_restaurant"),
  MENU_ID_REQUIRED("domain.menu.id.required"),
  PRODUCT_ID_REQUIRED("domain.product.id.required"),
  PRODUCT_NAME_EXISTING("domain.product.name.existing");
  private final String code;

  MenuErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
