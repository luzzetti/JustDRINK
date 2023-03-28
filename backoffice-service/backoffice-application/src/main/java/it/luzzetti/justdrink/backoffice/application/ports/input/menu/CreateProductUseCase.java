package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import java.math.BigDecimal;

public interface CreateProductUseCase {

  Product createProduct(
      String nome, BigDecimal price, RestaurantId restaurantId, MenuSectionId sectionId);
}
