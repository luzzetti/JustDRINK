package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

public interface CreateMenuSectionUseCase {

  MenuSection createMenuSection(CreateMenuSectionCommand command);

  @Builder
  record CreateMenuSectionCommand(@NotNull UUID restaurantId, @NotNull @NotBlank String title) {}
}
