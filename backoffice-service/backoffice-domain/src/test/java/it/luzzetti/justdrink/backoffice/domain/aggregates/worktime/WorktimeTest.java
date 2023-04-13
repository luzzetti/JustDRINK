package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class WorktimeTest {

  private Worktime aTestWorktime;

  @BeforeEach
  public void setup() {
    aTestWorktime =
        Worktime.builder()
            .id(WorktimeId.empty())
            .restaurantId(RestaurantId.empty())
            .overrules(new ArrayList<>())
            .openings(new ArrayList<>())
            .build();
  }

  @Test
  @DisplayName("Opening Adding - Error expected")
  void whenAddingOpeningWithSameOpenAndCloseTime_thanAnExceptionIsThrown() {
    LocalTime aTime = LocalTime.of(20, 15, 0);
    LocalTime aSecondTime = LocalTime.of(20, 20, 0);

    Opening theInvalidOpening =
        Opening.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .openTime(aTime)
            .closeTime(aSecondTime)
            .build();

    assertThrows(
        ValidationException.class,
        () -> aTestWorktime.addOpening(theInvalidOpening),
        "Non è stata lanciata alcuna eccezione");
  }

  @Test
  @DisplayName("Opening Adding - Happy Case")
  void whenAddingValidOpening_thanItIsAdded() {
    LocalTime openingTime = LocalTime.of(8, 0, 0);
    LocalTime closingTime = LocalTime.of(13, 0, 0);

    Opening aValidOpening =
        Opening.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .openTime(openingTime)
            .closeTime(closingTime)
            .build();

    int expectedOpeningsSize = 1;
    aTestWorktime.addOpening(aValidOpening);

    int actualOpeningsSize = aTestWorktime.getOpenings().size();

    assertEquals(
        expectedOpeningsSize, actualOpeningsSize, "Dopo l'aggiunta, il valore non è quello atteso");
  }

  static List<Opening> invalidOpeningsProvider() {
    return List.of(
        Opening.builder()
            .openTime(LocalTime.of(8, 0, 0))
            .closeTime(LocalTime.of(13, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build(),
        Opening.builder()
            .openTime(LocalTime.of(7, 0, 0))
            .closeTime(LocalTime.of(14, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build(),
        Opening.builder()
            .openTime(LocalTime.of(9, 0, 0))
            .closeTime(LocalTime.of(12, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build(),
        Opening.builder()
            .openTime(LocalTime.of(9, 0, 0))
            .closeTime(LocalTime.of(14, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build(),
        Opening.builder()
            .openTime(LocalTime.of(7, 0, 0))
            .closeTime(LocalTime.of(12, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build());
  }

  @ParameterizedTest
  @DisplayName("Opening Adding - Expected overlapping")
  @MethodSource("invalidOpeningsProvider")
  void whenAddingOpeningThatOverlapsAnyOpenings(Opening opening) {

    Opening firstValidOpening =
        Opening.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .openTime(LocalTime.of(8, 0, 0))
            .closeTime(LocalTime.of(13, 0, 0))
            .dayOfWeek(DayOfWeek.MONDAY)
            .build();
    aTestWorktime.addOpening(firstValidOpening);

    assertThrows(
        ValidationException.class,
        () -> aTestWorktime.addOpening(opening),
        "Not validation throws!");
  }

  @Test
  @DisplayName("Opening adding - Open time is before close time")
  void whenAddingOpeningWhereTheOpenTimeIsBeforeTheCloseTime() {
    var theNewOpening =
        Opening.builder()
            .openTime(LocalTime.of(15, 0, 0))
            .closeTime(LocalTime.of(10, 0, 0))
            .build();
    assertThrows(
        ValidationException.class,
        () -> aTestWorktime.addOpening(theNewOpening),
        "Not validation throws");
  }
}
