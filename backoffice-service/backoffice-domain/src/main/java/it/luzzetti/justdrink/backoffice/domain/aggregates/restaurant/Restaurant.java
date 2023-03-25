package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.AggregateRoot;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import lombok.*;

@AggregateRoot
@Getter
@Builder
public class Restaurant {
  @Builder.Default private RestaurantId id = RestaurantId.empty();
  private String name;
  private Boolean enabled;
}
