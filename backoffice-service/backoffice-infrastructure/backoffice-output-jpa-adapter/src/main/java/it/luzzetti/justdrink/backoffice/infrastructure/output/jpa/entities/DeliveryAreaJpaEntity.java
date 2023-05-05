package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

@Entity
@Table(name = "DELIVERY_AREAS")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryAreaJpaEntity {

  @Id private UUID id;

  // Riusciamo a capire come zittire questo warning? Temo di aver saltato qualche config.
  private Polygon polygon;
}
