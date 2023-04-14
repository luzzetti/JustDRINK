package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import java.time.DayOfWeek;

public record OpeningResource(DayOfWeek dayOfWeek, Timeslot shift) {}
