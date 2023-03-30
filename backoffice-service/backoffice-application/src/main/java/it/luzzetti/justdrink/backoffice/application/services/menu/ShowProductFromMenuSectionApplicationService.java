package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowProductFromMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowProductFromMenuSectionApplicationService
    implements ShowProductFromMenuSectionQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public Product showProductOfMenuSection(ShowProductOfMenuSectionCommand command) {
    // Fetching resources
    Menu menu = findMenuPort.findMenuByRestaurantIdMandatory(command.restaurantId());
    MenuSectionId theMenuSectionId = command.menuSectionId();
    ProductId theProductId = command.productId();

    return menu.getProductFromSection(theMenuSectionId, theProductId);
  }
}
