package it.luzzetti.justdrink.backoffice.infrastructure.output.feign.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.PlaceRestControllerAdapter.GeoparseRequest;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.PlaceRestControllerAdapter.GeoparseResponse;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.dto.PlaceResource;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/***
 * Feign Client
 * https://www.baeldung.com/spring-cloud-openfeign
 */

@Component
@Qualifier("feignClientAdapter")
@Primary
@RequiredArgsConstructor
@Log4j2
public class GeocodingFeignClientAdapter implements FindCoordinatesPort {

  private final GeocodingClient geocodingClient;

  @Override
  public Optional<Coordinates> displayName(String addressName) {
    log.debug(() -> "Executing feign call to GeoService");

    GeoparseResponse geoparseResponse = remotelyGeoparse(addressName);

    // Vado di fretta, Gino mi aspetta ♩ ♪ ♫ ♬
    PlaceResource placeResource = geoparseResponse.results().get(0);

    Coordinates foundCoordinates =
        Coordinates.of(
            Latitude.of(placeResource.latitude()), Longitude.of(placeResource.longitude()));

    return Optional.of(foundCoordinates);
  }

  private GeoparseResponse remotelyGeoparse(String addressName) {

    Optional<Boolean> exactMatch = Optional.of(Boolean.FALSE);
    GeoparseRequest request = new GeoparseRequest(addressName, exactMatch);

    try {
      GeoparseResponse body = geocodingClient.geoparse(request).getBody();
      return Objects.requireNonNull(body);
    } catch (Exception ex) {
      log.error(() -> "Feign Failed ", ex);
      throw ex;
    }
  }
}
