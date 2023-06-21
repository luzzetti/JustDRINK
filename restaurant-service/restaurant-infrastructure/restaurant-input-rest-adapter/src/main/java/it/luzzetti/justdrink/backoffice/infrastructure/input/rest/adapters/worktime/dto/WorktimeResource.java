package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import java.util.List;

public record WorktimeResource(List<OpeningResource> openings, List<OverruleResource> overrules) {}
