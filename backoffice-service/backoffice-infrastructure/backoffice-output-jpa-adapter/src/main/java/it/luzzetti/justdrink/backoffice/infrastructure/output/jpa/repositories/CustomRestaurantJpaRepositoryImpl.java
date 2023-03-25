package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.QRestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
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
}
