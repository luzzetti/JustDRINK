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

  public static Menu newMenu() {
    return Menu.builder().restaurantId(RestaurantId.empty()).id(MenuId.empty()).build();
  }

  public static Menu newMenuForRestaurant(RestaurantId restaurantId) {
    return Menu.builder().restaurantId(restaurantId).id(MenuId.empty()).build();
  }
}
