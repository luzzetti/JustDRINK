package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import java.util.List;

public interface ClashingOpeningChecker {

  static void validate(Opening aNewOpening, List<Opening> openings) {

    // qui metteremo in piedi la catena di validazione per gli Openings!

    var firstValidation = new NoOverlapsOpeningsValidator(openings);
    var secondValidation = new NoSameOpenTimeAndCloseTime();
    var thirdValidation = new NoOpeningWithOpenTimeAfterCloseTimeValidator();

    firstValidation.setNextValidator(secondValidation);
    secondValidation.setNextValidator(thirdValidation);

    firstValidation.handleValidation(aNewOpening);
  }
}
