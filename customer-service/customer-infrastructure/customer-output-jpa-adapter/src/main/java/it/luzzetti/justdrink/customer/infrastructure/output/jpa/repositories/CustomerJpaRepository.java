package it.luzzetti.justdrink.customer.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer.CustomerJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {}
