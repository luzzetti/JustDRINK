package it.luzzetti.justdrink.backoffice.domain.services.worktime;

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
    var firstValidator = new UselessValidator();
    var secondValidator = new NoOpeningOverrulesOnOverlappingTimeslotsValidator(existingOverrules);
    var thirdValidator = new NoOpeningAndClosingOverrulesOnSameDayValidator(existingOverrules);

    /*
     * Hooking them up in sequence.
     * Link more validators here if needed...
     */
    firstValidator.setNextValidator(secondValidator);
    secondValidator.setNextValidator(thirdValidator);

    // Launching the chain from the first validator
    firstValidator.handleValidation(theNewOverrule);
  }
}
