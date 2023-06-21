package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import lombok.Data;

@Data
public class AddressResource {
  private String displayName;
  private double latitude;
  private double longitude;
}
