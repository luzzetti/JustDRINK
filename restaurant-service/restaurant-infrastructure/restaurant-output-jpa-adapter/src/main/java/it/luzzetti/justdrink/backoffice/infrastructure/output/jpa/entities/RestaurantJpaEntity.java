package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "RESTAURANTS")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantJpaEntity extends AuditableEntity<String> {

  @NotNull @NotBlank private String name;
  @NotNull private AddressJpaEmbeddable address;
  @NotNull private OwnerJpaEmbeddable owner;
  private boolean enabled;

  @ElementCollection
  @CollectionTable(name = "CUISINE", joinColumns = @JoinColumn(name = "RESTAURANT_ID"))
  @Column(name = "RESTAURANT_ID")
  private Set<CuisineJpaEmbeddable> cuisines;

  private String logoPath;
}
