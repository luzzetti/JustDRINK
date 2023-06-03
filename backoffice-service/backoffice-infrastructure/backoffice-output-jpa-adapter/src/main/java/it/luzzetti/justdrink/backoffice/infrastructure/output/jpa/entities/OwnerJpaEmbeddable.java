package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Vedi AddressJpaEmbeddable per maggiori interessanti informazioni
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class OwnerJpaEmbeddable {
  @Column(name = "owner_id", nullable = false)
  private UUID id;

  @Column(name = "owner_username", nullable = false)
  private String username;

  @Column(name = "owner_email", nullable = false)
  private String email;
}
