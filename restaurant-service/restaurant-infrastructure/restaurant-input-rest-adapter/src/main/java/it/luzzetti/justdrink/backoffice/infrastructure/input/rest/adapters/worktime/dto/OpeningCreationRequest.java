package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;

public record OpeningCreationRequest(@NotNull DayOfWeek dayOfWeek, @NotNull Timeslot shift) {}
