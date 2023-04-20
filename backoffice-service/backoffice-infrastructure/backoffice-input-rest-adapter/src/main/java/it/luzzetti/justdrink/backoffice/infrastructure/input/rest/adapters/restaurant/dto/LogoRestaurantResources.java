package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LogoRestaurantResources {

  private String message;
  private String url;

}
