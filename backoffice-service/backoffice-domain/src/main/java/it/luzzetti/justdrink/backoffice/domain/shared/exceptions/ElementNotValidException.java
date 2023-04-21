package it.luzzetti.justdrink.backoffice.domain.shared.exceptions;

public class ElementNotValidException extends ApplicationException {
  public ElementNotValidException(ErrorCode errorCode) {
    super(errorCode);
  }
}
