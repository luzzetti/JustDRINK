package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.*;

@Getter
@Builder
public class Restaurant {

  private final RestaurantId id;
  private String name; // Must be a required/mandatory field
  private Address address;
  @Builder.Default private Set<Cuisine> cuisines = new HashSet<>();
  @Builder.Default private Boolean enabled = Boolean.FALSE;

  // Aggregate Public methods

  public void changeAddress(Address newAddress) {
    // Validations
    this.address = newAddress;
  }

  // Aggregate Private methods

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

  public void addCuisine(Cuisine theNewCuisine) {
    if (this.cuisines.contains(theNewCuisine)) {
      throw new IllegalArgumentException("Cannot add a cuisine with the same name");
    }
    this.cuisines.add(theNewCuisine);
  }

  public void removeCuisine(Cuisine theCuisine) {
    if(!this.cuisines.contains(theCuisine)){
      throw new IllegalArgumentException("Cannot remove this cuisine - the restaurant not contain this cuisine");
    }
    this.cuisines.remove(theCuisine);
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends RestaurantBuilder {

    /* Adding validations as part of build() method. */
    public Restaurant build() {
      if (super.id == null || super.id.id() == null) {
        throw new IllegalArgumentException("a Worktime cannot be created with a NULL id");
      }

      if (super.name == null || super.name.isBlank()) {
        throw new IllegalArgumentException("A Restaurant name cannot be null nor empty");
      }

      if (super.address == null) {
        throw new IllegalArgumentException(
            "The restaurant address cannot be null. It has to be somewhere.");
      }

      return super.build();
    }
  }
}
