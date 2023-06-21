package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.UUID;
import org.springframework.data.repository.history.RevisionRepository;

public interface RestaurantHistoryJpaRepository
    extends RevisionRepository<RestaurantJpaEntity, UUID, Integer> {}
