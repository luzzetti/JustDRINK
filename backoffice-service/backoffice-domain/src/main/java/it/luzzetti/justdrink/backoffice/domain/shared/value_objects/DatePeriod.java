package it.luzzetti.justdrink.backoffice.domain.shared.value_objects;

import java.time.LocalDate;
import java.time.Period;
import lombok.Builder;

@Builder
public record DatePeriod(LocalDate validFrom, LocalDate validThrough) {

  public boolean overlaps(DatePeriod that) {
    return (this.validFrom.isBefore(that.validThrough)
        && this.validThrough.isAfter(that.validFrom));
  }

  public static DatePeriodBuilder builder() {
    return new CustomBuilder();
  }

  private static class CustomBuilder extends DatePeriodBuilder {
    public DatePeriod build() {

      if (Period.between(super.validFrom, super.validThrough).isZero()) {
        throw new IllegalArgumentException("a DatePeriod should have a duration bigger than zero");
      }
      if (super.validThrough.isBefore(super.validFrom)) {
        throw new IllegalArgumentException("a DatePeriod cannot starts AFTER its ending");
      }

      return super.build();
    }
  }
}
