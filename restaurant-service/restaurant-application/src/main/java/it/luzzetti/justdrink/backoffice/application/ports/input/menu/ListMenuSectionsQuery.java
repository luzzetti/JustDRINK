package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public interface ListMenuSectionsQuery {

  List<MenuSection> listMenuSections(ListMenuSectionsCommand command);

  @Builder
  record ListMenuSectionsCommand(@NotNull UUID restaurantId) {}
}
