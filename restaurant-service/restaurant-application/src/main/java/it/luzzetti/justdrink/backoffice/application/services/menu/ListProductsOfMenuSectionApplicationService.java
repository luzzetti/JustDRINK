package it.luzzetti.justdrink.backoffice.application.services.menu;

import it.luzzetti.justdrink.backoffice.application.ports.input.menu.ListProductsOfMenuSectionQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListProductsOfMenuSectionApplicationService implements ListProductsOfMenuSectionQuery {

  private final FindMenuPort findMenuPort;

  @Override
  public List<Product> listProductsOfMenuSection(ListProductsOfMenuSectionCommand command) {
    // Fetching resources
    return findMenuPort
        .findMenuByRestaurantIdMandatory(command.restaurantId())
        .listProductsFromSection(command.menuSectionId());
  }
}
