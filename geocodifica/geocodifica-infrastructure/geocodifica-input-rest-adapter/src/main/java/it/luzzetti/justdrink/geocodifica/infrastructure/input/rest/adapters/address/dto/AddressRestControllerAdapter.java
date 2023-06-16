package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeocodeAddressUseCase;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.mappers.AddressWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geocodifica/1.0/address")
@Log4j2
@RequiredArgsConstructor
public class AddressRestControllerAdapter implements AddressRestController {

  private final GeocodeAddressUseCase geocodeAddressUseCase;

  private final AddressWebMapper addressWebMapper;

  @Override
  @GetMapping("/geocoding")
  public ResponseEntity<AddressResource> geocoding(@RequestParam String displayName) {
    log.warn(() -> "TEST chiamata OK " + displayName);

    GeocodeAddressUseCase.GeocodingAddressCommand command =
        GeocodeAddressUseCase.GeocodingAddressCommand.builder().displayName(displayName).build();
    Address geocoding = geocodeAddressUseCase.geocoding(command);
    AddressResource resource = addressWebMapper.toResource(geocoding);
    return ResponseEntity.ok(resource);
  }
}
