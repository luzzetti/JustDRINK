package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface MenuJpaRepository
    extends JpaRepository<MenuJpaEntity, UUID>, QuerydslPredicateExecutor<MenuJpaEntity> {

  @Query("""
      SELECT m FROM MenuJpaEntity m JOIN m.restaurant r WHERE r.id = :restaurantId
      """)
  Optional<MenuJpaEntity> findMenuByRestaurantId(@Param("restaurantId") UUID restaurantId);

  @Modifying
  @Query("""
      DELETE FROM MenuJpaEntity m where m.restaurant.id = :restaurantId
      """)
  void deleteMenuByRestaurantId(@Param("restaurantId") UUID restaurantId);
}
