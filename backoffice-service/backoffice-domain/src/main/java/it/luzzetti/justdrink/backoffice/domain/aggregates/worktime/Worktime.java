package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

// https://schema.org/OpeningHoursSpecification
// https://stackoverflow.com/a/4465072

import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.WorktimeId;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Worktime {

  private final WorktimeId id;
  private final RestaurantId restaurantId;
  @Builder.Default private final List<Opening> openings = new ArrayList<>();

  public static Worktime newWorktimeForRestaurant(RestaurantId restaurantId) {
    return Worktime.builder().id(WorktimeId.empty()).restaurantId(restaurantId).build();
  }

  public void addStandardOpening(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {
    Opening aStandardOpening =
        Opening.builder().dayOfWeek(dayOfWeek).openTime(openTime).closeTime(closeTime).build();

    // Validate Overlapping
    List<Opening> overlappingOpenings =
        openings.stream().filter(aStandardOpening::overlaps).toList();

    if (!overlappingOpenings.isEmpty()) {
      throw new IllegalArgumentException("Implement a specific OverlappingOpeningsException");
    }

    openings.add(aStandardOpening);
  }

  public List<Opening> getOpenings() {
    return Collections.unmodifiableList(openings);
  }

  public boolean isOpen(LocalDateTime aMomentInTime) {
    List<Opening> list = openings.stream().filter(o -> o.contains(aMomentInTime)).toList();
    return !list.isEmpty();
  }

  public Opening getLastCreatedOpening() {
    return openings.stream()
        .max(Comparator.comparing(Opening::getCreatedAt))
        .orElseThrow(IllegalArgumentException::new);
  }

  // TODO: I should return a combined view, one way or another

}
