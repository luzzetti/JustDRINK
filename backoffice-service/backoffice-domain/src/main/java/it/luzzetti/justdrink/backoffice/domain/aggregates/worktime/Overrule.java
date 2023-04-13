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
  /*
   * TODO:
   * Così come gli orari openTime e closeTime sono stati uniti in un oggetto di tipo 'fascia oraria'
   * o in inglese 'Timeslot', anche in questo caso potremmo unire i due campi 'validFrom' e 'validThrough'
   * in un unico campo 'validity' o 'validityPeriod' o con il nome che preferite.
   *
   * Il VO (Value Object) risultante, potrebbe essere un oggetto di tipo 'DatePeriod' comprendente,
   * per l'appunto, questi due campi.
   * (Seguire l'esempio di Timeslot)
   */
  private final LocalDate validFrom;
  private final LocalDate validThrough;
  private final DayOfWeek dayOfWeek;
  /*
   * TODO:
   * per quale motivo mantenere i due campi separati, quando la loro unione è concettualmente
   * una 'fascia oraria' ?
   * Modelliamo questo 'concetto' come è gia stato fatto dentro le 'Opening', utilizzando
   * il VO (Value Object) chiamato 'Timeslot'
   */
  private final LocalTime alternativeOpenTime;
  private final LocalTime alternativeCloseTime;
  private final Boolean closed;
  @Builder.Default private final Instant createdAt = Instant.now();

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
}
