package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "WORKTIMES")
@Getter
@Setter
@NoArgsConstructor
public class WorktimeJpaEntity extends AuditableEntity<String> {

  @OneToOne
  @JoinColumn(name = "RESTAURANT_ID")
  private RestaurantJpaEntity restaurant;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "WORKTIME_ID")
  @AuditJoinTable(name = "worktimes_openings_aud")
  private Set<OpeningJpaEntity> openings = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "WORKTIME_ID")
  @AuditJoinTable(name = "worktimes_overrules_aud")
  private Set<OverruleJpaEntity> overrules = new HashSet<>();
}
