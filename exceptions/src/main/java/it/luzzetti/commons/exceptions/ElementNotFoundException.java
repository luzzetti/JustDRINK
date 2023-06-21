package it.luzzetti.commons.exceptions;

public class ElementNotFoundException extends ApplicationException {
  public ElementNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
