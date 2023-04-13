package it.luzzetti.justdrink.backoffice.domain.services.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.AbstractValidator;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.time.LocalTime;

public class NoOpeningWithOpenTimeAfterCloseTimeValidator extends AbstractValidator<Opening> {

  @Override
  protected void validate(Opening opening) {
    LocalTime openTime = opening.getOpenTime();
    LocalTime closeTime = opening.getCloseTime();
    if(openTime.isAfter(closeTime)){
      throw new ValidationException("The Opening open time is after of close time");
    }
  }
}
