package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import static it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto.PlaceRestControllerAdapter.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PlaceRestController {
  @Operation(
      summary =
          "Dato un testo rappresentante un indirizzo, lo converte in un 'Place' con indirizzo e coordinate")
  @PostMapping("places:geoparse")
  ResponseEntity<GeoparseResponse> geoparse(@RequestBody @Valid GeoparseRequest request);
}
