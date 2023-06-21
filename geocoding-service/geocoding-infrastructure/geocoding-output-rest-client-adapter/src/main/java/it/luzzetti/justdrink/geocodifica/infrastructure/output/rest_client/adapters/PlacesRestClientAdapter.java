package it.luzzetti.justdrink.geocodifica.infrastructure.output.rest_client.adapters;

import it.luzzetti.justdrink.geocodifica.application.ports.output.FindPlacesPort;
import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery.GeoparsedQueryBuilder;
import it.luzzetti.justdrink.geocodifica.domain.shared.MatchType;
import it.luzzetti.justdrink.geocodifica.infrastructure.output.rest_client.mappers.PlaceRestClientMapper;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Log4j2
@RequiredArgsConstructor
public class PlacesRestClientAdapter implements FindPlacesPort {

  private final PlaceRestClientMapper placeMapper;

  @Override
  public GeoparsedQuery<Place> findPlacesFromAddress(String address) {

    List<Place> matchingPlaces = findPlacesFromExternalService(address);

    var resultBuilder = GeoparsedQuery.<Place>builder().query(address);

    if (matchingPlaces.size() == 1) {
      return createExactResult(matchingPlaces, resultBuilder);

    } else if (matchingPlaces.size() > 1) {
      return createApproximateResult(matchingPlaces, resultBuilder);

    } else {
      return createEmptyResult(resultBuilder);
    }
  }

  private static GeoparsedQuery<Place> createEmptyResult(
      GeoparsedQueryBuilder<Place> resultBuilder) {
    return resultBuilder.matchType(MatchType.NONE).results(Collections.emptyList()).build();
  }

  private static GeoparsedQuery<Place> createApproximateResult(
      List<Place> matchingPlaces, GeoparsedQueryBuilder<Place> resultBuilder) {
    return resultBuilder.matchType(MatchType.APPROXIMATE).results(matchingPlaces).build();
  }

  private static GeoparsedQuery<Place> createExactResult(
      List<Place> matchingPlaces, GeoparsedQueryBuilder<Place> resultBuilder) {
    return resultBuilder
        .matchType(MatchType.EXACT)
        .results(Collections.singletonList(matchingPlaces.get(0)))
        .build();
  }

  private static WebClient getWebClient() {
    return WebClient.builder()
        .baseUrl("https://geocode.maps.co")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  private List<Place> findPlacesFromExternalService(String address) {
    List<ExternalGeoparseResponse> results =
        getWebClient()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", address).build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<ExternalGeoparseResponse>>() {})
            .block();

    List<ExternalGeoparseResponse> responses =
        Objects.requireNonNullElse(results, Collections.emptyList());

    return responses.stream().map(placeMapper::toDomain).toList();
  }

  public record ExternalGeoparseResponse(
      Long place_id, String display_name, Double lat, Double lon) {}
}
