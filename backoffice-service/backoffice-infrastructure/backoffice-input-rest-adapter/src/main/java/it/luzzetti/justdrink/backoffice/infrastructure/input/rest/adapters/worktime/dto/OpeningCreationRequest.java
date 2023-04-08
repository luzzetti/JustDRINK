package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningCreationRequest(
    @NotNull DayOfWeek dayOfWeek, @NotNull LocalTime openTime, @NotNull LocalTime closeTime) {}
