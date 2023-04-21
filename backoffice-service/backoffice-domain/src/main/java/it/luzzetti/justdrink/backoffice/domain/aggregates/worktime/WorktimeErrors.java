package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ErrorCode;

public enum WorktimeErrors implements ErrorCode {
  ID_REQUIRED("domain.worktime.id.required");
  private final String code;

  WorktimeErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
