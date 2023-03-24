package it.luzzetti.justdrink.backoffice.application.services;

import it.luzzetti.justdrink.backoffice.application.ports.input.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListRestaurantsService implements ListRestaurantsQuery {

  private final ListRestaurantsPort listRestaurantsPort;

  @Override
  public List<Restaurant> listRestaurants(ListRestaurantsCommand command) {

    String filter = command.filter();
    Integer maxPageSize = command.maxPageSize();
    Integer offset = command.offset();

    return listRestaurantsPort.listRestaurants(filter, maxPageSize, offset);
  }
}
