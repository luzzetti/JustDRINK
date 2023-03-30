package it.luzzetti.justdrink.backoffice.application.ports.input.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public interface ListProductsOfMenuSectionQuery {

  List<Product> listProductsOfMenuSection(ListProductsOfmenuSectionCommand command) ;

  @Builder
  record ListProductsOfmenuSectionCommand( @NotNull UUID restaurantId,@NotNull UUID menuSectionId){}

}
