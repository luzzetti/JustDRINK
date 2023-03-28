package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.CreateProductUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.UpdateMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import java.math.BigDecimal;
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
  public Product createProduct(
      String nome, BigDecimal price, RestaurantId restaurantId, MenuSectionId sectionId) {

    Product theNewProduct = Product.newProduct(nome, price);
    Menu theMenu = findMenuPort.findMenuByRestaurantIdMandatory(restaurantId);

    theMenu.addProductToSection(theNewProduct, sectionId);
    Menu menu = updateMenuPort.updateMenu(theMenu);

    return menu.getLastCreatedProduct();
  }
}
