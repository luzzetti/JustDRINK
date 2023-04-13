package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OVERRULES")
@Getter
@Setter
@NoArgsConstructor
public class OverruleJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private LocalDate validFrom;
  private LocalDate validThrough;
  private DayOfWeek dayOfWeek;
  private LocalTime alternativeOpenTime;
  private LocalTime alternativeCloseTime;
  private Boolean closed;

  private Instant createdAt;
}