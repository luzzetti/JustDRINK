package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.validators;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import java.util.List;

public interface ClashingOpeningsChecker {

  static void validate(Opening aNewOpening, List<Opening> openings) {

    // Qui metteremo in piedi la catena di validazione per gli Openings!
    var firstValidation = new NoOpeningsWithOverlappingTimeslotsValidator(openings);

    firstValidation.handleValidation(aNewOpening);
  }
}
