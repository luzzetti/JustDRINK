package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantHistoryQuery;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantHistoryJpaRepository;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Component;

/***
 * Official docs are pretty good
 * <a href="https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#envers-basics">Envers Chapter</a>
 * <p>
 * Samples
 * <a href="https://denuwanhimangahettiarachchi.medium.com/maintain-the-data-versioning-info-with-spring-data-envers-42b6dfc19e27">Envers Samples</a>
 */

@Component
@RequiredArgsConstructor
@Log4j2
public class RestaurantHistoryAdapter implements ShowRestaurantHistoryQuery {

  private final RestaurantHistoryJpaRepository restaurantHistoryRepository;
  private final RestaurantJpaMapper restaurantJpaMapper;

  @Override
  public Map<Instant, Restaurant> showRestaurantHistory(ShowRestaurantHistoryCommand command) {

    // Trovo TUTTE le revisions e le metto in una mappa
    return restaurantHistoryRepository.findRevisions(command.restaurantId().id()).stream()
        .collect(
            Collectors.toMap(
                Revision::getRequiredRevisionInstant,
                r -> this.getDomainRestaurant(r.getEntity())));
  }

  private Restaurant getDomainRestaurant(RestaurantJpaEntity r) {
    return restaurantJpaMapper.toDomain(r);
  }
}
