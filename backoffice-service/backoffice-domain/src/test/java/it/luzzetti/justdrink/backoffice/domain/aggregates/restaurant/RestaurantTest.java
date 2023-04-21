package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ApplicationException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestaurantTest {

  @Test
  @DisplayName("Restaurant Creating - No id Error")
  void whenCreatingANewRestaurantWithoutId_throwsIllegalArgumentException() {
    var theRestaurantBuilder =
        Restaurant.builder()
            .id(null)
            .address(
                Address.builder()
                    .displayName("via pippo")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .name("Fagiano's restaurant");
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Restaurant Creating - No name Error")
  void whenCreatingANewRestaurantWithoutName_throwsIllegalArgumentException() {
    var theRestaurantBuilder =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .address(
                Address.builder()
                    .displayName("via pippo")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .name(null);
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Restaurant Creating - No coordinates Error")
  void whenCreatingANewRestaurantWithoutCoordinates_throwsIllegalArgumentException() {
    var theRestaurantBuilder =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .address(null)
            .name("Fagiano's restaurant");
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Add Cuisine to Restaurant - Same Cuisine add")
  void whenAddingCuisineWithTheSameName_throwsIllegalArgumentException() {
    Restaurant theRestaurant =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .address(
                Address.builder()
                    .displayName("via pippo")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .name("Fagiano's restaurant")
            .build();

    Cuisine theSameCuisine = Cuisine.of("Giapponese");

    theRestaurant.addCuisine(theSameCuisine);
    assertThrows(ApplicationException.class, () -> theRestaurant.addCuisine(theSameCuisine));
  }

  @Test
  @DisplayName("Remove Cuisine from Restaurant - Not Existing Cuisine")
  void whenRemoveCuisineFromRestaurantAndTheCuisineNotExist_throwsElementNotFOundException() {

    Cuisine japanCuisine = Cuisine.of("Giapponese");
    Cuisine italianCuisine = Cuisine.of("Italiano");

    Restaurant theRestaurant =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .address(
                Address.builder()
                    .displayName("via pippo")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .name("Fagiano's restaurant")
            .cuisines(Set.of(japanCuisine))
            .build();

    assertThrows(ElementNotFoundException.class, () -> theRestaurant.removeCuisine(italianCuisine));
  }

  /*
   * TODO:
   * Invece di ricreare ogni volta un ristorante, scrivi il codice una volta sola utilizzando la
   * @BeforeEach
   */

}
