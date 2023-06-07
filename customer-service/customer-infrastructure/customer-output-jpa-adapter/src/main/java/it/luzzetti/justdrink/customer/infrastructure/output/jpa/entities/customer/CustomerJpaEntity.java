package it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
@NoArgsConstructor
public class CustomerJpaEntity {

  @Id private UUID id;
  @NotNull @NotBlank private String firstName;
  @NotNull @NotBlank private String lastName;

  // TODO: fai delle prove di rimozione figli per testare l'orphanRemoval!

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "CUSTOMER_ID")
  private Set<AddressJpaEntity> addressBook = new HashSet<>();
}
