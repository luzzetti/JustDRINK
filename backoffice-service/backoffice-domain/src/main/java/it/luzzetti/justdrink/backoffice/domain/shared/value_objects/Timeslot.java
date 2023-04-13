package it.luzzetti.justdrink.backoffice.domain.shared.value_objects;

import java.time.Duration;
import java.time.LocalTime;
import lombok.Builder;

/***
 * This class represents a timeslot (fascia oraria)
 */
@Builder
public record Timeslot(LocalTime from, LocalTime through) {

  /***
   * This method checks if this instance timeslot overlaps with the one given in input.
   * To check 2 timeslots we usually execute 4 checks:
   * A. starts before & ends after
   * B. starts before & ends during
   * C. starts during & ends during
   * D. starts during & ends after
   * <p>
   * This is a shorter form to execute the same check.
   * <a href="https://stackoverflow.com/a/325964">...</a>
   *
   * @return true if timeslots collides. false otherwise
   */
  public boolean overlaps(Timeslot that) {
    return (this.from.isBefore(that.through) && this.through.isAfter(that.from));
  }

  public boolean contains(LocalTime theLocalTime) {
    return this.from.isBefore(theLocalTime) && this.through.isAfter(theLocalTime);
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
  public static TimeslotBuilder builder() {
    return new CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends TimeslotBuilder {

    /* Adding validations as part of build() method. */
    public Timeslot build() {

      if (Duration.between(super.from, super.through).isZero()) {
        throw new IllegalArgumentException("a timeslot should have a duration bigger than zero");
      }

      if (super.from.isAfter(super.through)) {
        throw new IllegalArgumentException("a timeslot cannot starts AFTER its ending");
      }

      return super.build();
    }
  }
}
