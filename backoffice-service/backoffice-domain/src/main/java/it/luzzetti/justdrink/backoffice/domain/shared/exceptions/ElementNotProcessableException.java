package it.luzzetti.justdrink.backoffice.domain.shared.exceptions;

public class ElementNotProcessableException extends ApplicationException {
  public ElementNotProcessableException(ErrorCode errorCode) {
    super(errorCode);
  }
}
