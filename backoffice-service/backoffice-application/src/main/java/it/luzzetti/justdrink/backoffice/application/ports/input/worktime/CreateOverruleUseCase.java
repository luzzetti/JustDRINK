package it.luzzetti.justdrink.backoffice.application.ports.input.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.DatePeriod;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import lombok.Builder;

public interface CreateOverruleUseCase {

  Overrule createOpeningOverrule(CreateOpeningOverruleCommand command);

  Overrule createClosingOverrule(CreateClosingOverruleCommand command);

  @Builder
  record CreateOpeningOverruleCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull DatePeriod validity,
      @NotNull DayOfWeek dayOfWeek,
      @NotNull Timeslot alternativeShift) {}

  @Builder
  record CreateClosingOverruleCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull DatePeriod validity,
      @NotNull DayOfWeek dayOfWeek) {}
}
