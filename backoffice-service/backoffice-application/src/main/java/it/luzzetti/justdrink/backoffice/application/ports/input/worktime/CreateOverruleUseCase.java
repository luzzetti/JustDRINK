package it.luzzetti.justdrink.backoffice.application.ports.input.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

public interface CreateOverruleUseCase {

  Overrule createOpeningOverrule(CreateOpeningOverruleCommand command);

  Overrule createClosingOverrule(CreateClosingOverruleCommand command);

  @Builder
  record CreateOpeningOverruleCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull LocalDate validFrom,
      @NotNull LocalDate validThrough,
      @NotNull DayOfWeek dayOfWeek,
      @NotNull LocalTime alternativeOpenTime,
      @NotNull LocalTime alternativeCloseTime) {}

  @Builder
  record CreateClosingOverruleCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull LocalDate validFrom,
      @NotNull LocalDate validThrough,
      @NotNull DayOfWeek dayOfWeek) {}
}
