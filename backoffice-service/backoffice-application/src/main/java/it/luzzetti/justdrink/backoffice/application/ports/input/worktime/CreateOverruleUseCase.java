package it.luzzetti.justdrink.backoffice.application.ports.input.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

public interface CreateOverruleUseCase {

  Overrule createOverrule(CreateOverruleCommand command);

  @Builder
  record CreateOverruleCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull LocalDate validFrom,
      @NotNull LocalDate validThrough,
      @NotNull DayOfWeek dayOfWeek,
      @NotNull LocalTime alternativeOpenTime,
      @NotNull LocalTime alternativeCloseTime) {}
}
