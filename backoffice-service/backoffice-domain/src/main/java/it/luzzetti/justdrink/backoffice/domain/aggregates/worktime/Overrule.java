package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.DatePeriod;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import java.time.DayOfWeek;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Overrule {
  private OverruleId id;
  private final DatePeriod validity;
  private final DayOfWeek dayOfWeek;
  private final Timeslot alternativeShift;
  private final Boolean closed;
  @Builder.Default private final Instant createdAt = Instant.now();

  public boolean overlapsValidity(Overrule that) {
    return this.validity.overlaps(that.validity);
  }

  public boolean overlapsAlternativeShift(Overrule that) {

    // a 'closingOverrule' never overlaps.
    if (Boolean.TRUE.equals(that.closed) || Boolean.TRUE.equals(this.closed)) {
      return false;
    }

    if (this.dayOfWeek != that.dayOfWeek) {
      return false;
    }

    return this.alternativeShift.overlaps(that.alternativeShift);
  }
}
