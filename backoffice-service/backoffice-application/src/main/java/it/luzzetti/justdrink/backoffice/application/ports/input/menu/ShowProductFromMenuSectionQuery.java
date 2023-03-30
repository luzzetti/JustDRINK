package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ShowProductFromMenuSectionQuery {

  Product showProductOfMenuSection(ShowProductOfMenuSectionCommand command);

  @Builder
  record ShowProductOfMenuSectionCommand(
      @NotNull RestaurantId restaurantId,
      @NotNull MenuSectionId menuSectionId,
      @NotNull ProductId productId) {}
}
