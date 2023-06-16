package it.luzzetti.justdrink.geocodifica.infrastructure.output.rest_client.adapters;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.geocodifica.NonUniqueResultException;
import it.luzzetti.justdrink.geocodifica.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.AddressErrors;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Log4j2
@RequiredArgsConstructor
public class CoordinatesRestClientAdapter implements FindCoordinatesPort {

  @Override
  public List<Coordinate> displayName(String displayName) {
    WebClient webClient = getWebClient();

    try {
      List<Place> places = result(displayName, webClient);

      return places.stream()
          .map(CoordinatesRestClientAdapter::getCoordinate)
          .collect(Collectors.toList());

    } catch (Exception ex) {
      throw new ElementNotProcessableException(AddressErrors.IMPOSSIBLE_TO_GEOCODE);
    }
  }

  private static Coordinate getCoordinate(Place place) {
    Double latitudine = place.lat();
    Double longitudine = place.lon();

    return new CoordinateXY(latitudine, longitudine);
  }

  private static WebClient getWebClient() {
    return WebClient.builder()
        .baseUrl("https://geocode.maps.co")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  private static List<Place> result(String displayName, WebClient webClient) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", displayName).build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Place>>() {})
        .block();
  }

  record Place(Long place_id, String display_name, Double lat, Double lon) {}
}
