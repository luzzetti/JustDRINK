package it.luzzetti.justdrink.backoffice.application.services.menu;

import static it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId.from;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateMenuSectionUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.UpdateMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateMenuSectionApplicationService implements CreateMenuSectionUseCase {

  private final FindMenuPort findMenuPort;
  private final UpdateMenuPort updateMenuPort;

  @Override
  @Transactional
  public MenuSection createMenuSection(CreateMenuSectionCommand command) {
    log.debug(() -> String.format("createMenuSection(%s)", command));

    // Fetching Data
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(from(command.restaurantId()));

    // Use Case
    theMenu.createSection(command.title());

    // Persisting data
    Menu theUpdatedMenu = updateMenuPort.updateMenu(theMenu);

    // Returning a response
    return theUpdatedMenu.getLastCreatedSection();
  }
}
