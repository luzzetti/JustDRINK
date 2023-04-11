package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.util.Collection;
import java.util.List;

class NoOpeningOverrulesOnOverlappingTimeslotsValidator extends AbstractValidator<Overrule> {
  private final Collection<Overrule> existingOverrules;

  public NoOpeningOverrulesOnOverlappingTimeslotsValidator(List<Overrule> existingOverrules) {
    this.existingOverrules = existingOverrules;
  }

  /***
   * In the same day, two overrules with overlapping TimeSlots cannot coexist
   */
  @Override
  protected void validate(Overrule thisOverrule) {

    for (Overrule thatOverrule : existingOverrules) {

      boolean validityOverlaps = thisOverrule.overlapsValidity(thatOverrule);
      boolean isSameDayOfWeek = thisOverrule.getDayOfWeek().equals(thatOverrule.getDayOfWeek());
      boolean openingOverlaps = thisOverrule.overlapsOpening(thatOverrule);

      if (validityOverlaps && isSameDayOfWeek && openingOverlaps) {
        throw new ValidationException(
            "The new overrule is clashing with %s. They cannot have overlapping timeslots"
                .formatted(thatOverrule));
      }
    }

    /*
     * If no exception is thrown, validation passed, and the next validator
     * will be called from the superclass, thanks to the 'template pattern'
     */
  }
}
