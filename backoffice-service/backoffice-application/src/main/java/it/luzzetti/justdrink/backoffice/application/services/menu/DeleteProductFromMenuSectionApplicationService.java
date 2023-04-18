package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteProductFromMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteProductFromMenuSectionApplicationService
    implements DeleteProductFromMenuSectionUseCase {

  private final FindMenuPort findMenuPort;
  private final SaveMenuPort saveMenuPort;

  @Override
  public void deleteProduct(DeleteProductCommand command) {

    RestaurantId restaurantId = RestaurantId.from(command.restaurantId());
    MenuSectionId menuSectionId = MenuSectionId.from(command.menuSectionId());
    ProductId productId = ProductId.from(command.productId());
    Menu menu = findMenuPort.findMenuByRestaurantIdMandatory(restaurantId);

    menu.removeProductFromSection(menuSectionId, productId);

    saveMenuPort.saveMenu(menu);
  }
}
