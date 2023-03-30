package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ProductOfMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductOfMenuSectionApplicationService implements ProductOfMenuSectionQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public Product productOfMenuSection(ProductOfMenuSectionCommand command) {
    RestaurantId restaurantId = RestaurantId.from(command.restaurantId());
    Menu menu = findMenuPort.findMenuByRestaurantIdMandatory(restaurantId);
    MenuSectionId menuSectionId = MenuSectionId.from(command.menuSectionId());
    ProductId productId = ProductId.from(command.productId());

    return menu.getProductofSection(menuSectionId, productId);
  }
}
