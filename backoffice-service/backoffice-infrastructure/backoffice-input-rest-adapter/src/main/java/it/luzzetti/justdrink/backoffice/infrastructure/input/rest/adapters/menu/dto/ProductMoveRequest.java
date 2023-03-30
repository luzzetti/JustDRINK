package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductMoveRequest(@NotNull UUID productId, @NotNull UUID targetMenuSection) {}
