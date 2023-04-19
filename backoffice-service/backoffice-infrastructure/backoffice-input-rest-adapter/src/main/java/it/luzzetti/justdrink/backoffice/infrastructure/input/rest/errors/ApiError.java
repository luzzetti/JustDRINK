package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.errors;

import lombok.Builder;
import lombok.Getter;

/*
 * TODO: This is just a PoC.
 * the structure will be refined soon
 */
@Builder
@Getter
public class ApiError {
  private String message;
}
