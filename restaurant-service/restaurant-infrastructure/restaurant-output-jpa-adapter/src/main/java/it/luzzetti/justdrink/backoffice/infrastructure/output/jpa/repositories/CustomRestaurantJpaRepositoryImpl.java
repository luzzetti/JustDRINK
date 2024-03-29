package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.QRestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomRestaurantJpaRepositoryImpl implements CustomRestaurantJpaRepository {

  private final EntityManager entityManager;

  /*
   * More Info: "https://samuel-mumo.medium.com/dynamic-queries-and-querydsl-jpa-support-in-spring-a1b4e233084b"
   * More Info: "http://querydsl.com/static/querydsl/4.0.1/reference/html_single/#d0e227"
   */
  @Override
  public List<RestaurantJpaEntity> findAll(String filter, Integer maxPageSize, Integer offset) {

    var restaurant = QRestaurantJpaEntity.restaurantJpaEntity;

    return new JPAQueryFactory(entityManager)
        .selectFrom(restaurant)
        .where(restaurant.name.containsIgnoreCase(filter))
        .orderBy(restaurant.id.asc())
        .offset(offset)
        .limit(maxPageSize)
        .fetch();
  }

  @Override
  public Optional<RestaurantJpaEntity> findRestaurantByName(String restaurantName) {

    var restaurant = QRestaurantJpaEntity.restaurantJpaEntity;

    RestaurantJpaEntity theFoundRestaurant =
        new JPAQueryFactory(entityManager)
            .selectFrom(restaurant)
            .where(restaurant.name.equalsIgnoreCase(restaurantName))
            .fetchOne();

    return Optional.ofNullable(theFoundRestaurant);
  }
}
