package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.util.List;

public interface ListRestaurantsPort {
  List<Restaurant> listRestaurants(String filter, Integer maxPageSize, Integer offset);
}
