package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place;

import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeoparsePlaceUseCase;
import it.luzzetti.justdrink.geocodifica.application.ports.input.address.GeoparsePlaceUseCase.GeoparsePlaceCommand;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.dto.PlaceResource;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.mappers.PlacesWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
@RequiredArgsConstructor
@Log4j2
public class PlaceRestControllerAdapter implements PlaceRestController {

  private final GeoparsePlaceUseCase geoparsePlaceUseCase;

  private final PlacesWebMapper placesWebMapper;

  @Override
  @PostMapping("places:geoparse")
  public ResponseEntity<GeoparseResponse> geoparse(@RequestBody @Valid GeoparseRequest request) {

    var command =
        GeoparsePlaceCommand.builder()
            .address(request.address())
            .exactMatch(request.exactMatch().orElse(Boolean.FALSE))
            .build();

    GeoparsedQuery<Place> result = geoparsePlaceUseCase.geoparse(command);

    var response = placesWebMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  public record GeoparseRequest(@NotNull @NotBlank String address, Optional<Boolean> exactMatch) {}

  public record GeoparseResponse(String query, String matchType, List<PlaceResource> results) {}
}
