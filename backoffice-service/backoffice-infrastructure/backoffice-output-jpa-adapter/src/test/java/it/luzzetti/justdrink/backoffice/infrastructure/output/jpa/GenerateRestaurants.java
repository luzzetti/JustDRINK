package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;import java.util.ArrayList;import java.util.List;

public class GenerateRestaurants {

  public static List<Restaurant> generateRestaurants() {

    var result = new ArrayList<Restaurant>();
    result.add(Restaurant.builder().name("Tana del cibo").build());
    result.add(Restaurant.builder().name("Food 'n' Drinks").build());
    result.add(Restaurant.builder().name("Poisoned Food").build());
    result.add(Restaurant.builder().name("Christia'n'drinks").build());
    result.add(Restaurant.builder().name("JustFood").build());
    return result;
  }
}
