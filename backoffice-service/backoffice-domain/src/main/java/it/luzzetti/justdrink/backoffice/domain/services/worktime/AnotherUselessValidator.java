package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;

public class AnotherUselessValidator extends AbstractValidator<Opening> {
  @Override
  protected void validate(Opening opening) {
    // It doesn't do anything!
  }
}
