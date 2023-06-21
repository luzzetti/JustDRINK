package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.GenerateProductIdPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateProductApplicationService implements CreateProductUseCase {

  private final FindMenuPort findMenuPort;
  private final SaveMenuPort saveMenuPort;
  private final GenerateProductIdPort generateProductIdPort;

  @Override
  public Product createProduct(CreateProductCommand command) {

    // Fetching-Creating resource
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(command.restaurantId());

    ProductId theGeneratedProductId = generateProductIdPort.nextProductIdentifier();
    Product theNewProduct =
        Product.builder()
            .id(theGeneratedProductId)
            .name(command.name())
            .price(command.price())
            .build();

    MenuSectionId theSectionId = command.sectionId();

    // Executing the Business-Rule
    theMenu.addProductToSection(theNewProduct, theSectionId);

    // Persisting data
    Menu menu = saveMenuPort.saveMenu(theMenu);
    return menu.getSectionByIdMandatory(theSectionId).getProductById(theGeneratedProductId);
  }
}
