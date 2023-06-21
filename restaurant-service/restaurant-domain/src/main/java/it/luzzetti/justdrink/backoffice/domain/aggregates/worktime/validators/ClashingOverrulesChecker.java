package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.validators;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.util.List;

public interface ClashingOverrulesChecker {

  static void validate(Overrule theNewOverrule, List<Overrule> existingOverrules)
      throws ValidationException {

    /*
     * Instantiation of all the validators.
     * Add more validators here if needed...
     */
    var firstValidator = new NoOpeningOverrulesWithOverlappingTimeslotsValidator(existingOverrules);
    var secondValidator = new NoOpeningAndClosingOverrulesOnSameDayValidator(existingOverrules);

    /*
     * Hooking them up in sequence.
     * Link more validators here if needed...
     */
    firstValidator.setNextValidator(secondValidator);

    // Launching the chain from the first validator
    firstValidator.handleValidation(theNewOverrule);
  }
}
