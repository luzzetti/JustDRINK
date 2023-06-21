package it.luzzetti.justdrink.geocodifica.application.services.address;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeoparsePlaceUseCase;
import it.luzzetti.justdrink.geocodifica.application.ports.output.FindPlacesPort;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.PlaceErrors;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GeoparsePlaceApplicationsService implements GeoparsePlaceUseCase {

  private final FindPlacesPort findPlacesPort;

  /***
   * Se l'utente non chiede il match esatto, restituisci qualunque risultato
   * Se l'utente chiede un match esatto, restituisci solo se hai un match esatto
   * Il caso rimanente, è quando l'utente ha chiesto un match esatto e non l'ha trovato. In tal caso lancio un errore
   */
  @Override
  public GeoparsedQuery<Place> geoparse(@Valid GeoparsePlaceCommand command) {

    GeoparsedQuery<Place> results = findPlacesPort.findPlacesFromAddress(command.address());

    if (!command.exactMatch()) {
      return results;

    } else if (results.isExactMatch()) {
      return results;

    } else {
      throw new ElementNotProcessableException(PlaceErrors.IMPOSSIBLE_TO_GEOCODE);
    }
  }
}
