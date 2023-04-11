package it.luzzetti.justdrink.backoffice.application.ports.input.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Builder;

public interface CreateOpeningUseCase {

  Opening createOpening(CreateOpeningCommand command);

  @Builder
  record CreateOpeningCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull DayOfWeek dayOfWeek,
      @NotNull LocalTime openTime,
      @NotNull LocalTime closeTime) {}
}
