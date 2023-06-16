package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.geocodifica.NonUniqueResultException;
import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeocodeAddressUseCase;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.AddressErrors;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.mappers.AddressWebMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geocoding/1.0/address")
@Log4j2
@RequiredArgsConstructor
public class AddressRestControllerAdapter implements AddressRestController {

  private final GeocodeAddressUseCase geocodeAddressUseCase;

  private final AddressWebMapper addressWebMapper;

  @Override
  @GetMapping("/geocoding-multi")
  public ResponseEntity<List<AddressResource>> multiGeocoding(@RequestParam String displayName) {
    GeocodeAddressUseCase.GeocodingAddressCommand command =
        GeocodeAddressUseCase.GeocodingAddressCommand.builder().displayName(displayName).build();

    List<Address> addresses = geocodeAddressUseCase.geocoding(command);

    List<AddressResource> resources =
        addresses.stream().map(addressWebMapper::toResource).collect(Collectors.toList());

    return ResponseEntity.ok(resources);
  }

  @Override
  @GetMapping("/geocoding")
  public ResponseEntity<AddressResource> geocoding(@RequestParam String displayName) {

    GeocodeAddressUseCase.GeocodingAddressCommand command =
        GeocodeAddressUseCase.GeocodingAddressCommand.builder().displayName(displayName).build();

    List<Address> addresses = geocodeAddressUseCase.geocoding(command);

    validaRisultato(addresses);

    Address address = addresses.get(0);

    AddressResource resource = addressWebMapper.toResource(address);
    return ResponseEntity.ok(resource);
  }

  private static void validaRisultato(List<Address> addresses) {
    if (addresses.isEmpty()) {
      throw new ElementNotProcessableException(AddressErrors.IMPOSSIBLE_TO_GEOCODE);
    }

    if (addresses.size() > 1) {
      throw new NonUniqueResultException(AddressErrors.IMPOSSIBLE_TO_DETERMINATE);
    }
  }
}
