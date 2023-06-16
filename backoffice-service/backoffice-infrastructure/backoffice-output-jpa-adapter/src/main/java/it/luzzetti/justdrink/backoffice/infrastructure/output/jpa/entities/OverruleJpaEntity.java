package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "OVERRULES")
@Getter
@Setter
@NoArgsConstructor
public class OverruleJpaEntity extends BaseEntity {
  private LocalDate validFrom;
  private LocalDate validThrough;
  private DayOfWeek dayOfWeek;
  private LocalTime alternativeOpenTime;
  private LocalTime alternativeCloseTime;
  private Boolean closed;
}
