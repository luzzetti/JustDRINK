package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;

public class NoSameOpenTimeAndCloseTime extends AbstractValidator<Opening> {

  @Override
  protected void validate(Opening opening) {
    int hourOpen = opening.getOpenTime().getHour();
    int hourClose = opening.getCloseTime().getHour();
    int minuteOpen = opening.getOpenTime().getMinute();
    int minuteClose = opening.getCloseTime().getMinute();

    if (hourOpen == hourClose && minuteOpen == minuteClose) {
      throw new ValidationException("Stai inserendo lo stesso orario di apertura e chiusura");

    }
  }
}
