package it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "ADDRESSBOOK")
public class AddressJpaEntity {

  @Id private UUID id;
  @NotNull @NotBlank private String name;
  private String city;
  private String postalCode;
  private String street;
}
