package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

public interface ProductOfMenuSectionQuery {

  Product productOfMenuSection(ProductOfMenuSectionCommand command);

  @Builder
  record ProductOfMenuSectionCommand(
      @NotNull UUID restaurantId, @NotNull UUID menuSectionId, @NotNull UUID productId) {}
}
