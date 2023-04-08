package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Opening {
  private UUID id;
  private final DayOfWeek dayOfWeek;
  private final LocalTime openTime;
  private final LocalTime closeTime;
  @Builder.Default private final Instant createdAt = Instant.now();

  // Short way
  // https://stackoverflow.com/a/325964
  // Check equality edge cases
  public boolean overlapsOpening(Opening that) {
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

  // ####################################################
  // Qui viene "intercettato" il builder di lombok,
  // per poter aggiungere validazioni custom al .build()
  // e non avere mai domain-entity in uno stato invalido
  // ####################################################

  /**
   * Override the builder() method to return our custom builder instead of the Lombok generated
   * builder class.
   */
  public static OpeningBuilder builder() {
    return new CustomBuilder();
  }

  /**
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends OpeningBuilder {

    /** Adding validations as part of build() method. */
    public Opening build() {

      if (Duration.between(super.openTime, super.closeTime).isZero()) {
        throw new IllegalArgumentException("this opening value has no sense (idiot)");
      }

      if (MINUTES.between(super.openTime, super.closeTime) < 5) {
        throw new IllegalArgumentException("An opening duration must be AT LEAST 5 minutes");
      }

      if (super.openTime.isAfter(super.closeTime)) {
        throw new IllegalArgumentException("openTime must be PRECEDENT of closeTime");
      }

      return super.build();
    }
  }
}
