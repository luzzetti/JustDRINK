package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.List;

public class CustomRestaurantJpaRepositoryImpl implements CustomRestaurantJpaRepository {

  @Override
  public List<RestaurantJpaEntity> customQuery(String filter) {
    String url =
        "https://samuel-mumo.medium.com/dynamic-queries-and-querydsl-jpa-support-in-spring-a1b4e233084b";
    throw new UnsupportedOperationException(
        String.format("Not yet implemented. You can try it tho: %s", url));
  }
}
