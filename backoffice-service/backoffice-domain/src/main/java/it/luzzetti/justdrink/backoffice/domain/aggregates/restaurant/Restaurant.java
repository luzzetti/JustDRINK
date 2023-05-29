package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import it.luzzetti.commons.exceptions.ElementNotFoundException;
import it.luzzetti.commons.exceptions.ElementNotUniqueException;
import it.luzzetti.commons.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Restaurant {

  private final RestaurantId id;
  private String name;
  private Address address;
  private Owner owner;
  @Builder.Default private Set<Cuisine> cuisines = new HashSet<>();
  @Builder.Default private Boolean enabled = Boolean.FALSE;

  private String logoPath;

  // Aggregate Public methods
  public void changeAddress(Address newAddress) {
    // Validations
    if (newAddress == null) {
      throw new ElementNotValidException(RestaurantErrors.ADDRESS_REQUIRED);
    }

    this.address = newAddress;
  }

  public void changeLogo(String newPathLogo) {

    if (newPathLogo == null) {
      throw new ElementNotValidException(RestaurantErrors.PATH_REQUIRED);
    }

    this.logoPath = newPathLogo;
  }

  // Aggregate Private methods

  /*
   * TODO: Simone
   * tu hai gestito le Cuisines, ma quali sono le 'regole'?
   * Può esistere un ristorante senza cuisines? (Non vedo validazioni qui)
   * chi usa le API (Il frontendista), come fa a sapere quali sono le cuisines disponibili?
   * Le scrivi nella documentazione? (In tal caso, tanto valeva fare una enum)
   * O altrimenti, è il caso di fare un endpoint da cui (fetchare) le cuisines esistenti, no?
   *
   * Come si comporta JustEAT? Hai mai trovato un ristorante senza cuisines?
   * Serve un po' di analisi :)
   */
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
