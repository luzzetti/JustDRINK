package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.DatePeriod;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Overrule {
  private UUID id;
  private final DatePeriod datePeriod;
  private final DayOfWeek dayOfWeek;
  private final Timeslot alternativeTimeslot;
  private final Boolean closed;
  @Builder.Default private final Instant createdAt = Instant.now();

  public boolean overlapsValidity(Overrule that) {
    return this.datePeriod.overlaps(that.datePeriod);
  }

  public boolean overlapsOpening(Overrule that) {

    if (Boolean.TRUE.equals(that.closed) || Boolean.TRUE.equals(this.closed)) {
      // a close overrule never overlaps.
      return false;
    }

    if (this.dayOfWeek != that.dayOfWeek) {
      return false;
    }

    return this.alternativeTimeslot.overlaps(that.alternativeTimeslot);
  }
}
