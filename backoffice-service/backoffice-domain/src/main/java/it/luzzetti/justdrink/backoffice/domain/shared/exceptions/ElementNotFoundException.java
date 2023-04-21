package it.luzzetti.justdrink.backoffice.domain.shared.exceptions;

public class ElementNotFoundException extends ApplicationException {
  public ElementNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
