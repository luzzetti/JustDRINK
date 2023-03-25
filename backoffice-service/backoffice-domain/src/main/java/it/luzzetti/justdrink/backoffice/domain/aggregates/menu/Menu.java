package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Menu {

  private MenuId id;
  private RestaurantId restaurantId;
}
