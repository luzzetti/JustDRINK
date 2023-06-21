package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import it.luzzetti.commons.exceptions.ErrorCode;

public enum WorktimeErrors implements ErrorCode {
  ID_REQUIRED("domain.worktime.id.required"),
  OPENING_NOT_FOUND("domain.worktime.opening.opening_not_found"),
  OVERRULE_NOT_FOUND("domain.worktime.overrule.overrule_not_found");
  private final String code;

  WorktimeErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
