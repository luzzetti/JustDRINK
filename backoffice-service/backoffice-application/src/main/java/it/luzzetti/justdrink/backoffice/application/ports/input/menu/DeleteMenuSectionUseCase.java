package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

public interface DeleteMenuSectionUseCase {

  void deleteMenuSection(DeleteMenuSectionCommand command);

  @Builder
  record DeleteMenuSectionCommand(@NotNull UUID restaurantId, @NotNull UUID menuSectionId) {}
}
