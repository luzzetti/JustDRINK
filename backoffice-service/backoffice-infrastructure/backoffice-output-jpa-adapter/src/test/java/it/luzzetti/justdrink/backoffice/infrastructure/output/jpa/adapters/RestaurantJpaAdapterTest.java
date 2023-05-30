package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.commons.exceptions.ApplicationException;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.GenerateRestaurants;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.SpringDataConfiguration;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
@ContextConfiguration(classes = SpringDataConfiguration.class)
class RestaurantJpaAdapterTest {

  @Autowired EntityManager entityManager;
  @Autowired RestaurantJpaRepository restaurantJpaRepository;
  @Autowired RestaurantJpaMapper restaurantJpaMapper;
  private RestaurantJpaAdapter theAdapterUnderTest;

  // Test Values
  private static final String EXISTING_NAME = "Tana del cibo";

  @BeforeAll
  void beforeAll() {
    List<RestaurantJpaEntity> generated =
        GenerateRestaurants.generateRestaurants().stream()
            .map(restaurantJpaMapper::toEntity)
            .toList();

    restaurantJpaRepository.saveAll(generated);

    // Creating the adapter to be tested
    this.theAdapterUnderTest =
        new RestaurantJpaAdapter(restaurantJpaRepository, restaurantJpaMapper);
  }

  @BeforeEach
  void beforeEach() {}

  @AfterAll
  void afterAll() {
    restaurantJpaRepository.deleteAll();
  }

  @Test
  @DisplayName("Find Restaurant By Name - Happy Case")
  void whenQueryingByName_thenItFindOnlyOneResult() {

    Restaurant found = theAdapterUnderTest.findRestaurantByName(EXISTING_NAME);

    assertNotNull(found, "the restaurant has not been found, but it should've");
    assertNotNull(found.getId(), "the found restaurant should have an ID");
    assertNotNull(found.getName(), "the found restaurant should have a name. Maybe is a proxy?");
  }

  @Test
  @DisplayName("Create Restaurant - Happy Case")
  void whenCreatingRestaurant_thenItGetsCreated() {

    Restaurant aRestaurant =
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("aName")
            .address(
                Address.builder()
                    .displayName("via")
                    .coordinates(Coordinates.of(Latitude.of(0.0), Longitude.of(0.0)))
                    .build())
            .build();

    Restaurant theCreatedRestaurant = theAdapterUnderTest.saveRestaurant(aRestaurant);

    assertNotNull(aRestaurant);
    assertNotNull(aRestaurant.getId());
    System.out.println(theCreatedRestaurant);
  }

  @Test
  @DisplayName("Delete Restaurant - Happy Case")
  void whenDeletingRestaurant_thenItGetsDeleted() {

    Restaurant anExistingRestaurant = theAdapterUnderTest.findRestaurantByName(EXISTING_NAME);

    theAdapterUnderTest.deleteRestaurantById(anExistingRestaurant.getId());

    assertThrows(
        ApplicationException.class,
        () -> theAdapterUnderTest.findRestaurantByName(EXISTING_NAME),
        "the restaurant shouldn't exist anymore in the database");
  }
}
