package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

public interface DeleteProductFromMenuSectionUseCase {

  void deleteProduct(DeleteProductCommand command);

  @Builder
  record DeleteProductCommand(
      @NotNull UUID restaurantId, @NotNull UUID menuSectionId, UUID productId) {}
}
