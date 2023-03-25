package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.MenuResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface MenuWebMapper {
  MenuResource toResource(Menu theMenu);

  default UUID toUuid(RestaurantId restaurantId) {
    return restaurantId.id();
  }

  default UUID toUuid(MenuId menuId) {
    return menuId.id();
  }
}
