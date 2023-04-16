package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OpeningId;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Opening {
  private OpeningId id;
  private final DayOfWeek dayOfWeek;
  private final Timeslot shift;
  @Builder.Default private final Instant createdAt = Instant.now();

  /***
   * Two openings overlaps, if they have clashing timeslot on the same day of the week
   */
  public boolean overlapsShift(Opening that) {

    // Different days cannot overlap
    if (!this.dayOfWeek.equals(that.dayOfWeek)) {
      return false;
    }

    return this.shift.overlaps(that.shift);
  }

  public boolean contains(LocalDateTime aMomentInTime) {

    DayOfWeek thatDayOfWeek = aMomentInTime.getDayOfWeek();
    LocalTime thatLocalTime = aMomentInTime.toLocalTime();

    if (!this.dayOfWeek.equals(thatDayOfWeek)) {
      return false;
    }

    return this.shift.contains(thatLocalTime);
  }
}
