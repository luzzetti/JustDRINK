package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.locationtech.jts.geom.Polygon;

@Entity
@Audited
@Table(name = "DELIVERY_AREAS")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryAreaJpaEntity extends AuditableEntity<String> {

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
