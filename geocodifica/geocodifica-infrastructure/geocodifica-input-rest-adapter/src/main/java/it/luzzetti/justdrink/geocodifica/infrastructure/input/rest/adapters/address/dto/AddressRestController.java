package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface AddressRestController {

  public ResponseEntity<AddressResource> geocoding (@RequestParam String displayName);
}
