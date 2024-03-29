package it.luzzetti.justdrink.backoffice.application.services.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ListRestaurantsQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.ListRestaurantsPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ListRestaurantsApplicationService implements ListRestaurantsQuery {

  private final ListRestaurantsPort listRestaurantsPort;

  @Override
  public List<Restaurant> listRestaurants(ListRestaurantsCommand command) {

    String filter = command.filter();
    int pageSize = command.pageSize();
    int offset = command.offset();

    return listRestaurantsPort.listRestaurants(filter, pageSize, offset);
  }
}
