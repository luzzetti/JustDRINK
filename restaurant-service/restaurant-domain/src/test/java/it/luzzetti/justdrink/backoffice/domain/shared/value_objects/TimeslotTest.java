package it.luzzetti.justdrink.backoffice.domain.shared.value_objects;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeslotTest {

  @Test
  @DisplayName("Timeslot Invalid Creation - Same 'from' and 'through' time")
  void whenCreatingTimeslotWithSameFromAndThroughTime_thanAnExceptionIsThrown() {
    LocalTime aTime = LocalTime.of(20, 15, 0);
    LocalTime aSecondTime = LocalTime.of(20, 15, 0);

    var invalidTimeslotBuilder = Timeslot.builder().from(aTime).through(aSecondTime);

    assertThrows(IllegalArgumentException.class, invalidTimeslotBuilder::build);
  }

  @Test
  @DisplayName("Timeslot Invalid Creation - 'from' time is after 'through' time")
  void whenCreatingTimeslotWhereFromTimeIsAfterThroughTime_thanAnExceptionIsThrown() {

    // Setup phase
    LocalTime aLocalTime = LocalTime.of(15, 0, 0);
    LocalTime aPreviousLocalTime = LocalTime.of(10, 0, 0);

    var invalidTimeslotBuilder = Timeslot.builder().from(aLocalTime).through(aPreviousLocalTime);

    // Assert phase
    assertThrows(IllegalArgumentException.class, invalidTimeslotBuilder::build);
  }
}
