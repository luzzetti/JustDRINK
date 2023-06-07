package it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CustomerResource(@NotNull String firstName, @NotNull String lastName) {
}
