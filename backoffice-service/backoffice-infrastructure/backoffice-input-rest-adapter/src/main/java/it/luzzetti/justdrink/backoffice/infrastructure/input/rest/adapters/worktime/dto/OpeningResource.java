package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

// TODO: Gli orari...maronn...bisogna accordarsi sugli orari
public record OpeningResource(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {}
