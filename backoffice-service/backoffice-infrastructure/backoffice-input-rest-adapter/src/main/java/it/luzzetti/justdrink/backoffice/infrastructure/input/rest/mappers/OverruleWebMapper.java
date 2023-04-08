package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.ClosingOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper
public interface OverruleWebMapper {

  @Mapping(target = "overruleType", ignore = true)
  OverruleResource toResource(Overrule domain);

  /*
   * Il metodo qua sopra, mappa come al solito, MA...ma...OverruleResource è una classe astratta.
   * E quindi? chiederete voi.
   * Come fa a decidere quale implementazione della classe astratta deve istanziare papà? Diccelo!
   * Con l'ObjectFactory qua sotto.
   * Il metodo qua sotto prende l'oggetto di dominio, e fa una scelta di quale classe istanziare
   * in base a quello che gli diciamo noi.
   *
   * Ora, mappandolo con ObjectFactory, si limita ad ISTANZIARLO, poi chiama comunque il mapper
   * qua sopra per concludere l'implementazione del mapper. (La copia di tutti i campi)
   *
   * In realtà è questo sopra che prima di copiare i campi, chiama il factory qua sotto.
   */

  @ObjectFactory
  default OverruleResource factory(Overrule domain) {

    if (Boolean.TRUE.equals(domain.getClosed())) {
      ClosingOverruleResource closingOverruleResource = new ClosingOverruleResource();
      closingOverruleResource.setOverruleType(OverruleType.CLOSING);
      return closingOverruleResource;

    } else {
      OpeningOverruleResource openingOverruleResource = new OpeningOverruleResource();
      openingOverruleResource.setOverruleType(OverruleType.OPENING);
      return openingOverruleResource;
    }
  }
}
