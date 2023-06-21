package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

public record RestaurantCreationRequest(
    @NotNull @NotBlank String name,
    @NotNull @NotBlank String addressName,
    Optional<Coordinates> coordinates,
    Set<CuisineResource> cuisines) {}
