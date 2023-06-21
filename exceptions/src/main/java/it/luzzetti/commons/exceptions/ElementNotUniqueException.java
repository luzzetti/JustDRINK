package it.luzzetti.commons.exceptions;

public class ElementNotUniqueException extends ApplicationException {
  public ElementNotUniqueException(ErrorCode errorCode) {
    super(errorCode);
  }
}
