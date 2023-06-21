package it.luzzetti.justdrink.backoffice.domain.shared.validation;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
