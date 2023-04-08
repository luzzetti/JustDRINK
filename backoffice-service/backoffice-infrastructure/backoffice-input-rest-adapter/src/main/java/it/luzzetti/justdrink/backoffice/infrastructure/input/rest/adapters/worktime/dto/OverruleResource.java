package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record OverruleResource(
    LocalDate validFrom,
    LocalDate validThrough,
    DayOfWeek dayOfWeek,
    LocalTime alternativeOpenTime,
    LocalTime alternativeCloseTime) {}
