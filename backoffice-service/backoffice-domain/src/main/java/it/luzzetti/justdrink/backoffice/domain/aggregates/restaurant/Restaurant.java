package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotUniqueException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
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
  private Owner owner;
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

  public boolean isOwnedBy(Owner anOwner) {
    return Objects.equals(this.owner, anOwner);
  }

  public void changeOwnership(Owner aNewOwner) {
    this.owner = aNewOwner;
  }

  /* Builder Override */
  public static RestaurantBuilder builder() {
    return new CustomBuilder();
  }

  private static class CustomBuilder extends RestaurantBuilder {

    /* Validations as part of build() method. */
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
