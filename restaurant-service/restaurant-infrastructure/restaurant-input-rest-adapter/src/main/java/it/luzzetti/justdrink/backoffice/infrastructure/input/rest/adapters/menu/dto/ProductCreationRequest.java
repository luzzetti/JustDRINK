package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductCreationRequest(@NotNull @NotBlank String name, @NotNull BigDecimal price) {}
