package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.util.Collection;
import java.util.List;

public class NoOverlapsOpeningsValidator extends AbstractValidator<Opening> {
  private final Collection<Opening> existingOpenings;

  public NoOverlapsOpeningsValidator(List<Opening> existingOpenings) {
    this.existingOpenings = existingOpenings;
  }

  @Override
  protected void validate(Opening newOpening) {

    for (Opening existingOpening : existingOpenings) {
      // controllo se le sovrapposizioni sono valide, se si lancio l'errore di validazione
      boolean validityOverlaps = newOpening.overlapsOpening(existingOpening);

      if (validityOverlaps) {
        throw new ValidationException(
            "The new Opening is clashing with %s. A store cannot be both Open and Closed in the same day"
                .formatted(existingOpening));
      }
    }
  }
}
