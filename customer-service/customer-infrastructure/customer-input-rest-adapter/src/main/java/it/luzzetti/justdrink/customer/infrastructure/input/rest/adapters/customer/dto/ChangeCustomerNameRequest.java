package it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChangeCustomerNameRequest(
    @NotNull @NotBlank String firstName, @NotNull @NotBlank String lastName) {}
