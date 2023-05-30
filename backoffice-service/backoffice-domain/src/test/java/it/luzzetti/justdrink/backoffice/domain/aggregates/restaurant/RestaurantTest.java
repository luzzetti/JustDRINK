package it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.commons.exceptions.ApplicationException;
import it.luzzetti.commons.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant.RestaurantBuilder;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestaurantTest {

  private RestaurantBuilder theRestaurantBuilder;

  @BeforeEach
  void init() {
    theRestaurantBuilder =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .address(
                Address.builder()
                    .displayName("via pippo")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .name("Fagiano's restaurant");
  }

  @Test
  @DisplayName("Restaurant Creating - No id Error")
  void whenCreatingANewRestaurantWithoutId_thenThrowsApplicationException() {
    theRestaurantBuilder.id(null);
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Restaurant Creating - No name Error")
  void whenCreatingANewRestaurantWithoutName_thenThrowsApplicationException() {
    theRestaurantBuilder.name(null);
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Restaurant Creating - No address Error")
  void whenCreatingANewRestaurantWithoutAddress_thenThrowsApplicationException() {
    theRestaurantBuilder.address(null);
    assertThrows(ApplicationException.class, theRestaurantBuilder::build);
  }

  @Test
  @DisplayName("Add Cuisine to Restaurant - Same Cuisine add")
  void whenAddingCuisineWithTheSameName_thenThrowsApplicationException() {
    Restaurant theRestaurant = theRestaurantBuilder.build();
    Cuisine theSameCuisine = Cuisine.of("Giapponese");

    theRestaurant.addCuisine(theSameCuisine);

    assertThrows(ApplicationException.class, () -> theRestaurant.addCuisine(theSameCuisine));
  }

  @Test
  @DisplayName("Remove Cuisine from Restaurant - Not Existing Cuisine")
  void whenRemoveCuisineFromRestaurantAndTheCuisineNotExist_throwsElementNotFOundException() {

    Cuisine japanCuisine = Cuisine.of("Giapponese");
    Cuisine italianCuisine = Cuisine.of("Italiano");

    Restaurant theRestaurant = theRestaurantBuilder.cuisines(Set.of(japanCuisine)).build();

    assertThrows(ElementNotFoundException.class, () -> theRestaurant.removeCuisine(italianCuisine));
  }
}
