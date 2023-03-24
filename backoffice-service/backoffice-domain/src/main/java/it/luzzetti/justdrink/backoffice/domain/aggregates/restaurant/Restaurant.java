package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.AggregateRoot;
import java.util.UUID;
import lombok.*;

@Getter
@Builder
public class Restaurant implements AggregateRoot {
  @Builder.Default private final RestaurantId id = RestaurantId.empty();
  private String name;
  @Builder.Default private Boolean enabled = Boolean.FALSE;

  public record RestaurantId(UUID id) {
    public static RestaurantId empty() {
      return new RestaurantId(null);
    }
  }
}
