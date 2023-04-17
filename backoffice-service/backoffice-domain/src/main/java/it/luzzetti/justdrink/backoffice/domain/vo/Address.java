package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.Builder;

/***
 * Name is a textual representation of the address
 * eg: Via Cairoli 15, 01100, Italia
 * <p>
 * Coordinates are, obviously, coordinates
 */
@Builder
public record Address(String name, Coordinates coordinates) {}
