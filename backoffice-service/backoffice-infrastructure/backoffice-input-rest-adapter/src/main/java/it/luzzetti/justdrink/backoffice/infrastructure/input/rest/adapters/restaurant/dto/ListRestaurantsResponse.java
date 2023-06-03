package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ListRestaurantsResponse(List<RestaurantListElement> restaurants) {}
