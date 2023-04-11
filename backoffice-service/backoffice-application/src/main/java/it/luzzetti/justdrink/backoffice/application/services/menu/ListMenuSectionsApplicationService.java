package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListMenuSectionsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListMenuSectionsApplicationService implements ListMenuSectionsQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public List<MenuSection> listMenuSections(ListMenuSectionsCommand command) {
    // Fetching Data
    RestaurantId theRestaurantId = RestaurantId.from(command.restaurantId());
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(theRestaurantId);

    return theMenu.getSections();
  }
}
