package it.luzzetti.justdrink.backoffice.domain.shared.value_objects;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DatePeriod(LocalDate from, LocalDate through) {

  public boolean overlaps(DatePeriod that) {
    return (this.from.isBefore(that.through) && this.through.isAfter(that.from));
  }

  // Lombok's Builder Override to add validations

  public static DatePeriodBuilder builder() {
    return new CustomBuilder();
  }

  private static class CustomBuilder extends DatePeriodBuilder {
    public DatePeriod build() {

      if (super.through.isBefore(super.from)) {
        throw new IllegalArgumentException("a DatePeriod cannot starts AFTER its ending");
      }

      return super.build();
    }
  }
}
