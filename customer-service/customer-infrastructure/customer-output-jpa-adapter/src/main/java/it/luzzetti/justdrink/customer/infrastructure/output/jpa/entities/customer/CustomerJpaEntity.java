package it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

  @NotNull @NotBlank private String name;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "CUSTOMER_ID")
  private Set<AddressJpaEntity> addressBook = new HashSet<>();
}
