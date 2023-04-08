package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record OverruleCreationRequest(
    @NotNull LocalDate validFrom,
    @NotNull LocalDate validThrough,
    @NotNull DayOfWeek dayOfWeek,
    @NotNull LocalTime alternativeOpenTime,
    @NotNull LocalTime alternativeCloseTime) {}
