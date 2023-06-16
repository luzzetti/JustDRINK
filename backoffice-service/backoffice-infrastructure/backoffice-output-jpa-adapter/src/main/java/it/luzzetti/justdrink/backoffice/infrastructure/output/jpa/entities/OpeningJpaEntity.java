package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "OPENINGS")
@Getter
@Setter
@NoArgsConstructor
public class OpeningJpaEntity extends BaseEntity {
  private DayOfWeek dayOfWeek;
  private LocalTime openTime;
  private LocalTime closeTime;
}
