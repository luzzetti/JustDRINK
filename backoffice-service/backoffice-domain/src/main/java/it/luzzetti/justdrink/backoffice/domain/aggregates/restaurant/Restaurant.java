package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import lombok.*;

@Getter
@Builder
public class Restaurant {
  /*
   * TODO: Seguire l'idea di WorkTime per una generazione 'early' degli ID.
   * con buone probabilità, manterremo questa strategia per tutto il progetto
   */
  @Builder.Default private final RestaurantId id = RestaurantId.empty();
  private String name; // Must be a required/mandatory field
  private Address address;

  @Builder.Default private Boolean enabled = Boolean.FALSE;

  // ##########################//
  // Lombok's Builder Override //
  // ##########################//

  /**
   * Override the builder() method to return our custom builder instead of the Lombok generated
   * builder class.
   */
  public static RestaurantBuilder builder() {
    return new CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends RestaurantBuilder {

    /* Adding validations as part of build() method. */
    public Restaurant build() {

      if (super.name == null || super.name.isBlank()) {
        throw new IllegalArgumentException("A Restaurant name cannot be null nor empty");
      }

      return super.build();
    }
  }
}
