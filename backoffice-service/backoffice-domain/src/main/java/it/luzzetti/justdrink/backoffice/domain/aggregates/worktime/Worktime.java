package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

// https://schema.org/OpeningHoursSpecification
// https://stackoverflow.com/a/4465072

import it.luzzetti.justdrink.backoffice.domain.services.worktime.ClashingOpeningChecker;
import it.luzzetti.justdrink.backoffice.domain.services.worktime.ClashingOverrulesChecker;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
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
  @Builder.Default private final List<Overrule> overrules = new ArrayList<>();

  public static Worktime newWorktimeForRestaurant(RestaurantId restaurantId) {
    return Worktime.builder().id(WorktimeId.empty()).restaurantId(restaurantId).build();
  }

  public void addOpening(Opening aNewOpening) {

    // Validate Overlapping
    ClashingOpeningChecker.validate(aNewOpening, openings);

    openings.add(aNewOpening);
  }

  public void addOverrule(Overrule aNewOverrule) {

    ClashingOverrulesChecker.validate(aNewOverrule, overrules);

    overrules.stream()
        .filter(aNewOverrule::overlapsValidity)
        .filter(aNewOverrule::overlapsOpening)
        .findFirst()
        .ifPresent(
            o -> {
              throw new IllegalArgumentException(
                  "An overrule with a clashing validity and opening period already exists: %s"
                      .formatted(o));
            });

    overrules.add(aNewOverrule);
  }

  public List<Opening> getOpenings() {
    return Collections.unmodifiableList(openings);
  }

  public List<Overrule> getOverrules() {
    return Collections.unmodifiableList(overrules);
  }

  public Opening getLastCreatedOpening() {
    return openings.stream()
        .max(Comparator.comparing(Opening::getCreatedAt))
        .orElseThrow(IllegalArgumentException::new);
  }

  public Overrule getLastCreatedOverrule() {
    return overrules.stream()
        .max(Comparator.comparing(Overrule::getCreatedAt))
        .orElseThrow(IllegalArgumentException::new);
  }
}
