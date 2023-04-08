package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class Opening {
  private final DayOfWeek dayOfWeek;
  private final LocalTime openTime;
  private final LocalTime closeTime;

  private Opening(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {
    this.dayOfWeek = dayOfWeek;
    this.openTime = openTime;
    this.closeTime = closeTime;
  }

  public static Opening of(DayOfWeek dayOfWeek, LocalTime opens, LocalTime closes) {

    return new Opening(dayOfWeek, opens, closes);
  }

  // Short way
  // https://stackoverflow.com/a/325964
  // Check equality edge cases
  public boolean overlaps(Opening that) {
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

  private boolean isLocalTimeContained(LocalTime theLocalTime) {
    return this.getOpenTime().isBefore(theLocalTime) && this.getCloseTime().isAfter(theLocalTime);
  }

  private boolean isSameDayOfWeek(DayOfWeek theDayOfWeek) {
    return this.getDayOfWeek() == theDayOfWeek;
  }
}
