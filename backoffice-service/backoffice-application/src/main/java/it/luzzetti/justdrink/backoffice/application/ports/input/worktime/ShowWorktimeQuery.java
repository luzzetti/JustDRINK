package it.luzzetti.justdrink.backoffice.application.ports.input.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ShowWorktimeQuery {

  Worktime showWorktime(ShowWorktimeCommand command);

  @Builder
  record ShowWorktimeCommand(@NotNull RestaurantId restaurantId) {}
}
