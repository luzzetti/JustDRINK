package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotUniqueException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import java.util.HashSet;
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
    if (newAddress == null) {
      throw new ElementNotValidException(RestaurantErrors.ADDRESS_REQUIRED);
    }

    this.address = newAddress;
  }

  // Aggregate Private methods

  // Lombok's Builder Override //

  /**
   * Override the builder() method to return our custom builder instead of the Lombok generated
   * builder class.
   */
  public static RestaurantBuilder builder() {
    return new CustomBuilder();
  }

  public void addCuisine(Cuisine theNewCuisine) {
    if (this.cuisines.contains(theNewCuisine)) {
      throw new ElementNotUniqueException(RestaurantErrors.CUISINE_ALREADY_EXISTING);
    }
    this.cuisines.add(theNewCuisine);
  }

  public void removeCuisine(Cuisine theCuisine) {
    if (!this.cuisines.contains(theCuisine)) {
      throw new ElementNotFoundException(RestaurantErrors.CUISINE_NOT_EXISTING);
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
        throw new ElementNotValidException(RestaurantErrors.ID_REQUIRED);
      }

      if (super.name == null || super.name.isBlank()) {
        throw new ElementNotValidException(RestaurantErrors.NAME_REQUIRED);
      }

      if (super.address == null) {
        throw new ElementNotValidException(RestaurantErrors.ADDRESS_REQUIRED);
      }

      return super.build();
    }
  }
}
