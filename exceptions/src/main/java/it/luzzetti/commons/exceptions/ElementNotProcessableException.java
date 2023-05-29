package it.luzzetti.commons.exceptions;

public class ElementNotProcessableException extends ApplicationException {
  public ElementNotProcessableException(ErrorCode errorCode) {
    super(errorCode);
  }
}
