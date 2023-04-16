package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

// https://schema.org/OpeningHoursSpecification
// https://stackoverflow.com/a/4465072

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.validators.ClashingOpeningsChecker;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.validators.ClashingOverrulesChecker;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OpeningId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Worktime {

  private final WorktimeId id;
  private final RestaurantId restaurantId;

  @Builder.Default private final List<Opening> openings = new ArrayList<>();
  @Builder.Default private final List<Overrule> overrules = new ArrayList<>();

  public void addOpening(Opening aNewOpening) {

    // Validate Overlapping
    ClashingOpeningsChecker.validate(aNewOpening, openings);

    openings.add(aNewOpening);
  }

  /***
   * a new overrule can be added only if it doesn't clash with the existing ones.
   * In case of a clash, a ValidationException will be thrown
   */
  public void addOverrule(Overrule aNewOverrule) {
    ClashingOverrulesChecker.validate(aNewOverrule, overrules);

    overrules.add(aNewOverrule);
  }

  public List<Opening> getOpenings() {
    return Collections.unmodifiableList(openings);
  }

  public List<Overrule> getOverrules() {
    return Collections.unmodifiableList(overrules);
  }

  public Opening getOpeningById(OpeningId anOpeningId) {
    return openings.stream()
        .filter(o -> Objects.equals(o.getId(), anOpeningId))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "There is no Opening with id %s in this worktime".formatted(anOpeningId)));
  }

  public Overrule getOverruleById(OverruleId anOverruleId) {
    return overrules.stream()
        .filter(o -> Objects.equals(o.getId(), anOverruleId))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "There is no Overrule with id %s in this worktime".formatted(anOverruleId)));
  }

  // ####################################################
  // Lombok's Builder Override
  // ####################################################

  public static WorktimeBuilder builder() {
    return new Worktime.CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends WorktimeBuilder {

    /* Adding validations as part of build() method. */
    public Worktime build() {

      if (super.id == null || super.id.id() == null) {
        throw new IllegalArgumentException("a Worktime cannot be created with a NULL id");
      }

      return super.build();
    }
  }
}
