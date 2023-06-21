package it.luzzetti.justdrink.backoffice.infrastructure.output.feign.adapters;

import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.PlaceRestController;
import org.springframework.cloud.openfeign.FeignClient;

// api/1.0/places:geoparse
@FeignClient(value = "geocoding", url = "http://localhost:9191/api/1.0/")
public interface GeocodingClient extends PlaceRestController {}
