package it.luzzetti.justdrink.backoffice.domain.aggregates.worktime;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Overrule {
  private UUID id;
  private final LocalDate validFrom;
  private final LocalDate validThrough;
  private final DayOfWeek dayOfWeek;
  private final LocalTime alternativeOpenTime;
  private final LocalTime alternativeCloseTime;
  private final Boolean closed;
  @Builder.Default private final Instant createdAt = Instant.now();

  // ####################################################
  // Qui viene "intercettato" il builder di lombok,
  // per poter aggiungere validazioni custom al .build()
  // e non avere mai domain-entity in uno stato invalido
  // ####################################################

  /**
   * Override the builder() method to return our custom builder instead of the Lombok generated
   * builder class.
   */
  public static OverruleBuilder builder() {
    return new CustomBuilder();
  }

  public boolean overlapsValidity(Overrule that) {
    return (this.validFrom.isBefore(that.validThrough)
        && this.validThrough.isAfter(that.validFrom));
  }

  public boolean overlapsOpening(Overrule that) {

    if (Boolean.TRUE.equals(that.closed) || Boolean.TRUE.equals(this.closed)) {
      // a close overrule never overlaps.
      return false;
    }

    if (this.dayOfWeek != that.dayOfWeek) {
      return false;
    }

    return (this.alternativeOpenTime.isBefore(that.alternativeCloseTime)
        && this.alternativeCloseTime.isAfter(that.alternativeOpenTime));
  }

  /**
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends OverruleBuilder {

    /** Adding validations as part of build() method. */
    public Overrule build() {

      return super.build();
    }
  }
}
