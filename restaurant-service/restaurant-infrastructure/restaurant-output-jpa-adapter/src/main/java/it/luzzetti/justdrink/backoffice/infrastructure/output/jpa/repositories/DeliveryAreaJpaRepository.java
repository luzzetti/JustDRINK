package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAreaJpaRepository extends JpaRepository<DeliveryAreaJpaEntity, UUID> {}
