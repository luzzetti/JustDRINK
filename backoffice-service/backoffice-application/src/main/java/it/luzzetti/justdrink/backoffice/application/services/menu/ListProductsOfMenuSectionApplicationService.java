package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListProductsOfMenuSectionApplicationService implements ListProductsOfMenuSectionQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public List<Product> listProductsOfMenuSection(ListProductsOfmenuSectionCommand command) {
    RestaurantId restaurantId = RestaurantId.from(command.restaurantId());
    Menu menu = findMenuPort.findMenuByRestaurantIdMandatory(restaurantId);
    MenuSectionId sectionId = MenuSectionId.from(command.menuSectionId());

    return menu.getProductsOfSection(sectionId);
  }
}
