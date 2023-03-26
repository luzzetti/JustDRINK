package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreationRequest(@NotNull @NotBlank String name) {}
