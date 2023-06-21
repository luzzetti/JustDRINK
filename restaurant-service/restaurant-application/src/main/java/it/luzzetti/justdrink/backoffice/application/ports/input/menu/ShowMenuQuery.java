package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ShowMenuQuery {

  Menu showMenu(ShowMenuCommand command);

  @Builder
  record ShowMenuCommand(@NotNull RestaurantId restaurantId) {}
}
