package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RESTAURANTS")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantJpaEntity {

  @Id
  private UUID id;

  @NotNull @NotBlank private String name;
  @NotNull private AddressJpaEmbeddable address;
  private boolean enabled;

  @ElementCollection
  @CollectionTable(name = "CUISINE", joinColumns = @JoinColumn(name = "RESTAURANT_ID"))
  @Column(name = "RESTAURANT_ID")
  private Set<CuisineJpaEmbeddable> cuisines;

  private String logoPath;
}
