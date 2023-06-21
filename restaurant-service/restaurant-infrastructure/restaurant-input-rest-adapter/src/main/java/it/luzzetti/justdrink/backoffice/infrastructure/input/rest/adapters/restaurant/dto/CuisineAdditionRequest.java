package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CuisineAdditionRequest(@NotNull @NotBlank String name) {}
