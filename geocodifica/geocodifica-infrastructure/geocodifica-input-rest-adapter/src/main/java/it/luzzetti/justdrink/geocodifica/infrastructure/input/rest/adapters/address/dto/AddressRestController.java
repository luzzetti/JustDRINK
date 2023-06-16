package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface AddressRestController {
  @Operation(summary = "Restituisce se presenti, una lista degli indirizzi associati a quel displayName")
  public ResponseEntity<List<AddressResource>> multiGeocoding(@RequestParam String displayName);

  @Operation(summary = "Restituisce un singolo indirizzo. Se sono presenti più indirizzi associati a quel displayName lancia un errore")
  ResponseEntity<AddressResource> geocoding(@RequestParam String displayName);
}
