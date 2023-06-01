package it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "ADDRESSBOOKS")
public class AddressJpaEntity {

  @Id private UUID id;

  @NotNull @NotBlank private String name;

  // Coordinate si
  private String city;
  private String postalCode;
  private String street;

}
