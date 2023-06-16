package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "SECTIONS")
@Getter
@Setter
@NoArgsConstructor
public class MenuSectionJpaEntity extends BaseEntity {

  private String title;

  // Composition vs aggregation vs association
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "SECTION_ID")
  @AuditJoinTable(name = "sections_products_aud")
  private Set<ProductJpaEntity> products = new HashSet<>();

  private Instant createdAt;
}
