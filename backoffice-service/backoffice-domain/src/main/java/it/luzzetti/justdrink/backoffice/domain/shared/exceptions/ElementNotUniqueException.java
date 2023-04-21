package it.luzzetti.justdrink.backoffice.domain.shared.exceptions;

public class ElementNotUniqueException extends ApplicationException {
  public ElementNotUniqueException(ErrorCode errorCode) {
    super(errorCode);
  }
}
