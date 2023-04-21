package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import jakarta.validation.constraints.NotNull;

public record CuisineResource(@NotNull String name) {}
