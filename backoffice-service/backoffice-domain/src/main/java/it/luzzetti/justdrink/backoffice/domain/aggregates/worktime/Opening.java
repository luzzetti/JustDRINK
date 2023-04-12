package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Opening {
  private final DayOfWeek dayOfWeek;
  private final LocalTime openTime;
  private final LocalTime closeTime;
  @Builder.Default private final Instant createdAt = Instant.now();
  private UUID id;

  // Short way
  // https://stackoverflow.com/a/325964
  // Check equality edge cases
  public boolean overlapsOpening(Opening that) {
    if (this.dayOfWeek != that.dayOfWeek) {
      return false;
    }

    return (this.openTime.isBefore(that.closeTime) && this.closeTime.isAfter(that.openTime));
  }

  public boolean contains(LocalDateTime aMomentInTime) {

    DayOfWeek theDayOfWeek = aMomentInTime.getDayOfWeek();
    LocalTime theLocalTime = aMomentInTime.toLocalTime();

    if (!isSameDayOfWeek(theDayOfWeek)) {
      return false;
    }
    if (!isLocalTimeContained(theLocalTime)) {
      return false;
    }

    return true;
  }

  private boolean isSameDayOfWeek(DayOfWeek theDayOfWeek) {
    return this.getDayOfWeek() == theDayOfWeek;
  }

  private boolean isLocalTimeContained(LocalTime theLocalTime) {
    return this.getOpenTime().isBefore(theLocalTime) && this.getCloseTime().isAfter(theLocalTime);
  }
}
