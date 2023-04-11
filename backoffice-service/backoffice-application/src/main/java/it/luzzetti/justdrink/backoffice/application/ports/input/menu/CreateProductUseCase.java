package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

public interface CreateProductUseCase {

  Product createProduct(CreateProductCommand command);

  // TODO: Generating a validator for Typed Ids

  @Builder
  record CreateProductCommand(
      @NotNull @NotEmpty String name,
      @NotNull BigDecimal price,
      @NotNull RestaurantId restaurantId,
      @NotNull MenuSectionId sectionId) {}
}
