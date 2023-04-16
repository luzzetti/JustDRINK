package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OPENINGS")
@Getter
@Setter
@NoArgsConstructor
public class OpeningJpaEntity {

  @Id @NotNull private UUID id;

  private DayOfWeek dayOfWeek;
  private LocalTime openTime;
  private LocalTime closeTime;
  private Instant createdAt;
}
