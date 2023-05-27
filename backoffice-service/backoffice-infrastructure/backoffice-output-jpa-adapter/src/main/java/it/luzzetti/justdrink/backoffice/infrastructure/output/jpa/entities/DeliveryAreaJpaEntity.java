package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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

  /* OneToOne Particolare
   * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
   *
   * Cascade per BugFix. Spero temporaneo:
   * https://stackoverflow.com/questions/28508385/org-hibernate-assertionfailure-null-identifier-onetoone-relationship
   */
  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @MapsId
  @JoinColumn(name = "ID")
  private RestaurantJpaEntity restaurant;

  private Polygon polygon;
}
