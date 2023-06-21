package it.luzzetti.justdrink.backoffice.domain.shared.value_objects;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatePeriodTest {

  @Test
  @DisplayName("DatePeriod Valid Creation - Same 'from' and 'through' time")
  void whenCreatingDatePeriodWithSameFromAndThroughTime_thanItWorks() {

    LocalDate aDate = LocalDate.of(1970, 1, 1);

    DatePeriod theCreatedPeriod = DatePeriod.builder().from(aDate).through(aDate).build();
    assertNotNull(theCreatedPeriod);
  }

  @Test
  @DisplayName("DatePeriod Invalid Creation - 'from' time is after 'through' time")
  void whenCreatingDatePeriodWhereFromTimeIsAfterThroughTime_thanAnExceptionIsThrown() {

    LocalDate firstDate = LocalDate.of(1970, 2, 1);
    LocalDate secondDate = LocalDate.of(1970, 1, 1);
    var datePeriodBuilder = DatePeriod.builder().from(firstDate).through(secondDate);

    assertThrows(IllegalArgumentException.class, datePeriodBuilder::build);
  }
}
