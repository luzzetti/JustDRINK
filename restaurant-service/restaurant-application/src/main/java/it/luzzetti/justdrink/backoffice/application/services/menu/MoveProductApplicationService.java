package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.MoveProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MoveProductApplicationService implements MoveProductUseCase {

  private final FindMenuPort findMenuPort;
  private final SaveMenuPort saveMenuPort;

  /*
   * IMPORTANTE
   * L'idea degli aggregates, nel DDD, è proprio quella di NON PERMETTERE di andare
   * ad intrallazzare direttamente con le classi al loro interno.
   *
   * Se io vado ad intrallazzare direttamente con i product, rischio di bypassare tutte le
   * regole che invece vengono inserite sul menu (Ad esempio, che può contenere max 100 prodotti)
   * e simili.
   *
   * Nel nostro caso, dato che l'aggregate è 'Menu', dobbiamo lavorare con quello.
   * (Oppure cambiamo l'architettura, e proviamo ad estrarre i products se necessario)
   */
  @Override
  public Product moveProduct(@Valid MoveProductCommand command) {

    // Fetching (the Aggregate Root) from Repository
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(command.restaurantId());

    // Delegating to the encapsulated UseCase
    theMenu.moveProductBetweenSections(
        command.productId(), command.sourceSectionId(), command.targetSectionId());

    // Saving the Aggregate Root
    Menu theUpdatedMenu = saveMenuPort.saveMenu(theMenu);

    return theUpdatedMenu.getProductFromSection(command.targetSectionId(), command.productId());
  }
}
