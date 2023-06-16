package it.luzzetti.justdrink.geocodifica.application.services.address;

import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeocodeAddressUseCase;
import it.luzzetti.justdrink.geocodifica.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;
import java.util.Optional;
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
  public Address geocoding(GeocodingAddressCommand command) {

    log.warn("TEST sono dentro al service");

    Address address = extractValidAddress(command.displayName());

    return address;
  }

  private Address extractValidAddress(String displayName) {

    Optional<Coordinate> coordinate = findCoordinatesPort.displayName(displayName);

    return Address.builder()
        .displayName(displayName)
        .coordinate(coordinate.orElseThrow(IllegalArgumentException::new))
        .build();

    //        return
    // Address.builder().displayName(addressName).coordinates(correctCoordinates).build();
  }
}
