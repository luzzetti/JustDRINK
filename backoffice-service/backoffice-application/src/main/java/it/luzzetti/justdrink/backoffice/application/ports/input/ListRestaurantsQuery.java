package it.luzzetti.justdrink.backoffice.application.ports.input;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;

public interface ListRestaurantsQuery {

  List<Restaurant> listRestaurants(ListRestaurantsCommand command);

  @Builder
  record ListRestaurantsCommand(@NotEmpty String filter, Integer maxPageSize, Integer offset) {}
}
