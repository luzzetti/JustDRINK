package it.luzzetti.justdrink.backoffice.infrastructure.output.rest_client.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Log4j2
@RequiredArgsConstructor
public class CoordinatesRestClientAdapter implements FindCoordinatesPort {

  /*
   * WEBCLIENT https://www.baeldung.com/spring-5-webclient
   * Migliore: https://reflectoring.io/spring-webclient/
   * QueryParameters: https://www.baeldung.com/webflux-webclient-parameters
   * Vedere se utile: https://httptoolkit.com/java/
   */

  /*
   * FIXME: this is a POC (Proof Of Concept) not ready for production
   */
  @Override
  public Optional<Coordinates> findCoordinatesByAddressName(String addressName) {

    var webClient =
        WebClient.builder()
            .baseUrl("https://geocode.maps.co")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    Place[] responses =
        webClient
            .get()
            .uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", addressName).build())
            .retrieve()
            .bodyToMono(Place[].class)
            .block();

    Place theGeocodedPlace =
        Arrays.stream(responses)
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("Oh sheeet, the geocoding didn't geocode!"));

    log.debug(() -> theGeocodedPlace);

    Coordinates theFoundCoordinates =
        Coordinates.of(Latitude.of(theGeocodedPlace.lat()), Longitude.of(theGeocodedPlace.lon()));

    return Optional.of(theFoundCoordinates);
  }

  record Place(Long place_id, String display_name, Double lat, Double lon) {}
}
