package it.luzzetti.justdrink.backoffice.domain.shared.validation;

public abstract class AbstractValidator<T> {
  protected AbstractValidator<T> nextValidator;

  public void handleValidation(T t) {

    validate(t);

    if (nextValidator != null) {
      nextValidator.handleValidation(t);
    }
  }

  /*
   * Template pattern.
   * The user will extend this class, and only override the "validate" method
   */
  protected abstract void validate(T t);

  public void setNextValidator(AbstractValidator<T> nextValidator) {
    this.nextValidator = nextValidator;
  }
}
