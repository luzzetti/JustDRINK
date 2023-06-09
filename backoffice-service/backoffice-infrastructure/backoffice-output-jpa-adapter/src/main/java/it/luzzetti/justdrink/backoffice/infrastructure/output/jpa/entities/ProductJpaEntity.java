package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
public class ProductJpaEntity {

  @Id private UUID id;

  private String name;
  private BigDecimal price;

  private Instant createdAt;
}
