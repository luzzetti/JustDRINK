package it.luzzetti.commons.exceptions;

public class ElementNotValidException extends ApplicationException {
  public ElementNotValidException(ErrorCode errorCode) {
    super(errorCode);
  }
}
