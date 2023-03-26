package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.DeleteMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.UpdateMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeleteMenuSectionApplicationService implements DeleteMenuSectionUseCase {

  private final FindMenuPort findMenuPort;
  private final UpdateMenuPort updateMenuPort;

  @Override
  @Transactional
  public void deleteMenuSection(DeleteMenuSectionCommand command) {
    log.debug(() -> String.format("deleteMenuSection(%s)", command));

    // Fetching required Data
    RestaurantId theRestaurantId = RestaurantId.from(command.restaurantId());
    MenuSectionId theMenuSectionId = MenuSectionId.from(command.menuSectionId());
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(theRestaurantId);

    // Use Case
    theMenu.removeSectionById(theMenuSectionId);

    // Persisting data
    updateMenuPort.updateMenu(theMenu);
  }
}