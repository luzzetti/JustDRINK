package it.luzzetti.justdrink.geocodifica.application.services.address;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeocodeAddressUseCase;
import it.luzzetti.justdrink.geocodifica.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.AddressErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GeocodeAddressApplicationsService implements GeocodeAddressUseCase {

  private final FindCoordinatesPort findCoordinatesPort;

  @Override
  public List<Address> geocoding(GeocodingAddressCommand command) {

    return extractValidAddress(command.displayName());
  }

  private List<Address> extractValidAddress(String displayName) {

    List<Coordinate> coordinates = findCoordinatesPort.displayName(displayName);

    if (coordinates.isEmpty()) {
      throw new ElementNotProcessableException(AddressErrors.IMPOSSIBLE_TO_GEOCODE);
    }

      return coordinates.stream().map(c -> Address.builder().coordinate(c).displayName(displayName).build()).collect(Collectors.toList());
  }
}
