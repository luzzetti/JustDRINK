package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
import it.luzzetti.justdrink.backoffice.domain.shared.validation.ValidationException;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    Opening theInvalidOpening =
        Opening.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .openTime(aTime)
            .closeTime(aTime)
            .build();

    assertThrows(ValidationException.class, () -> aTestWorktime.addOpening(theInvalidOpening));
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
}
