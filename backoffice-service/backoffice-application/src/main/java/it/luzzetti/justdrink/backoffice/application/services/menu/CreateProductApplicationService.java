package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.UpdateMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateProductApplicationService implements CreateProductUseCase {

  private final FindMenuPort findMenuPort;
  private final UpdateMenuPort updateMenuPort;

  @Override
  public Product createProduct(CreateProductCommand command) {

    // Fetching-Creating resource
    Product theNewProduct = Product.newProduct(command.name(), command.price());
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(command.restaurantId());
    MenuSectionId theSectionId = command.sectionId();

    // Executing the Business-Rule
    theMenu.addProductToSection(theNewProduct, theSectionId);

    // Persisting data
    Menu menu = updateMenuPort.updateMenu(theMenu);
    return menu.getLastCreatedProduct();
  }
}
