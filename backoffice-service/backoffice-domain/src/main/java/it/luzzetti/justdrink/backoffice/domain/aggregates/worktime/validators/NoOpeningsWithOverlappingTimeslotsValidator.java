package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.validators;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.util.Collection;
import java.util.List;

public class NoOpeningsWithOverlappingTimeslotsValidator extends AbstractValidator<Opening> {
  private final Collection<Opening> existingOpenings;

  public NoOpeningsWithOverlappingTimeslotsValidator(List<Opening> existingOpenings) {
    this.existingOpenings = existingOpenings;
  }

  @Override
  protected void validate(Opening newOpening) {

    for (Opening existingOpening : existingOpenings) {
      // controllo se le sovrapposizioni sono valide, se si lancio l'errore di validazione
      boolean validityOverlaps = newOpening.overlaps(existingOpening);

      if (validityOverlaps) {
        throw new ValidationException(
            "The new Opening is clashing with %s. Two opening's timeslots cannot overlap"
                .formatted(existingOpening));
      }
    }
  }
}
