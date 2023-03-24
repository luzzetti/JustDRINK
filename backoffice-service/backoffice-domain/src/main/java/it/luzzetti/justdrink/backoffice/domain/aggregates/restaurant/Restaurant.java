package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.AggregateRoot;
import java.util.UUID;
import lombok.*;

@Getter
@Builder
public class Restaurant implements AggregateRoot {
  @Builder.Default private final RestaurantId id = new RestaurantId();
  private String name;
  private Boolean enabled;

  public record RestaurantId(UUID id) {
    public RestaurantId() {
      this(null);
    }
  }
}
