package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface MoveProductUseCase {

  Product moveProduct(MoveProductCommand command);

  @Builder
  record MoveProductCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull ProductId productId,
      @NotNull MenuSectionId sourceSectionId,
      @NotNull MenuSectionId targetSectionId) {}
}
