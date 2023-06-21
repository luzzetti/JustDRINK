package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.List;
import java.util.UUID;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantJpaRepository
    extends JpaRepository<RestaurantJpaEntity, UUID>,
        QuerydslPredicateExecutor<RestaurantJpaEntity>,
        CustomRestaurantJpaRepository {

  @Query(
      """
          SELECT r
          FROM DeliveryAreaJpaEntity d
          JOIN d.restaurant r
          WHERE contains(d.polygon, :point) = true
          """)
  List<RestaurantJpaEntity> findByPointContainedInDeliveryArea(Point point);
}
