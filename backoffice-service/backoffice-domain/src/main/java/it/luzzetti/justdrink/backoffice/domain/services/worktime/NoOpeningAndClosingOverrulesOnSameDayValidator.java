package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.util.Collection;
import java.util.List;

class NoOpeningAndClosingOverrulesOnSameDayValidator extends AbstractValidator<Overrule> {

  private final Collection<Overrule> existingOverrules;

  public NoOpeningAndClosingOverrulesOnSameDayValidator(List<Overrule> existingOverrules) {
    this.existingOverrules = existingOverrules;
  }

  /***
   * In the same day, a store cannot be both Open and Closed
   */
  @Override
  protected void validate(Overrule thisOverrule) {

    for (Overrule thatOverrule : existingOverrules) {

      boolean validityOverlaps = thisOverrule.overlapsValidity(thatOverrule);
      boolean isSameDayOfWeek = thisOverrule.getDayOfWeek().equals(thatOverrule.getDayOfWeek());
      boolean isClashingOpeningWithClosing = thisOverrule.getClosed() != thatOverrule.getClosed();

      if (validityOverlaps && isSameDayOfWeek && isClashingOpeningWithClosing) {
        throw new ValidationException(
            "The new overrule is clashing with %s. A store cannot be both Open and Closed in the same day"
                .formatted(thatOverrule));
      }
    }

    /*
     * If no exception is thrown, validation passed, and the next validator
     * will be called from the superclass, thanks to the 'template pattern'
     */
  }
}
