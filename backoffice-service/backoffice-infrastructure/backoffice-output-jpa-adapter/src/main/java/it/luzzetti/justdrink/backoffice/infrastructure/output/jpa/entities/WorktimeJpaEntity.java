package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WORKTIMES")
@Getter
@Setter
@NoArgsConstructor
public class WorktimeJpaEntity {

  @Id private UUID id;

  @OneToOne
  @JoinColumn(name = "RESTAURANT_ID")
  private RestaurantJpaEntity restaurant;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "WORKTIME_ID")
  private Set<OpeningJpaEntity> openings = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "WORKTIME_ID")
  private Set<OverruleJpaEntity> overrules = new HashSet<>();
}
