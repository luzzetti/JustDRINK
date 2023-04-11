package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;

class UselessValidator extends AbstractValidator<Overrule> {

  @Override
  protected void validate(Overrule overrule) {

    /*
     * This doesn't do anything.
     * It's just here for demonstration purposes
     */

  }
}
