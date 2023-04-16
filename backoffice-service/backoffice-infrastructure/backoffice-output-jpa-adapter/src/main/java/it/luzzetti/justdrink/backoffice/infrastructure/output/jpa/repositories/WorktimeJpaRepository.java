package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.WorktimeJpaEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface WorktimeJpaRepository
    extends JpaRepository<WorktimeJpaEntity, UUID>,
        QuerydslPredicateExecutor<WorktimeJpaEntity>,
        CustomWorktimeJpaRepository {

  @Query(
      """
      SELECT w
      FROM WorktimeJpaEntity w
      JOIN w.restaurant r
      WHERE r.id = :restaurantId
      """)
  @EntityGraph(attributePaths = {"restaurant", "openings", "overrules"})
  Optional<WorktimeJpaEntity> findWorktimeByRestaurantId(@Param("restaurantId") UUID restaurantId);
}
