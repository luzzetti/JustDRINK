package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ShowMenuQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowMenuApplicationService implements ShowMenuQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public Menu showMenu(ShowMenuCommand command) {
    log.debug(() -> String.format("showMenu(%s)", command));

    return findMenuPort.findMenuByRestaurantIdMandatory(command.restaurantId());
  }
}
