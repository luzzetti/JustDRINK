package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningCreationRequest(
    DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {}
