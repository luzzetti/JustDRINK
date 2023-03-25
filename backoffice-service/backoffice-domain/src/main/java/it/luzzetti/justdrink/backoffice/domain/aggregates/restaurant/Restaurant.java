package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.*;

@Getter
@Builder
public class Restaurant {
  @Builder.Default private final RestaurantId id = RestaurantId.empty();
  private String name;
  @Builder.Default private Boolean enabled = Boolean.FALSE;
}
