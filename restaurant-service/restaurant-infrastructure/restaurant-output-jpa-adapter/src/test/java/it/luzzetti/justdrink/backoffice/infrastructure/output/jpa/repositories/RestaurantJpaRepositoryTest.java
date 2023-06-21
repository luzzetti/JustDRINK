package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.AddressJpaEmbeddable;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OwnerJpaEmbeddable;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/***
 * <a href="https://www.arhohuttunen.com/spring-boot-datajpatest/">Info</a>
 * <a href="https://jskim1991.medium.com/spring-boot-configure-testcontainers-in-your-test-code-this-way-417b221e55b">Info</a>
 * <a href="https://bootify.io/spring-boot/spring-boot-integration-tests-with-testcontainers.html">Info</a>
 * <a href="https://java.testcontainers.org/test_framework_integration/junit_5/">Info</a>
 * <a href="https://www.baeldung.com/spring-boot-testcontainers-integration-test">Info</a>
 * <a href="https://softwaremill.com/do-you-still-need-testcontainers-with-spring-boot-3-1/">Info</a>
 * <a href="https://www.arhohuttunen.com/test-data-builders/">Info</a>
 */

@DataJpaTest(
    properties = {
      "spring.liquibase.enabled=false",
      "spring.jpa.hibernate.ddl-auto=create",
    })
@ComponentScan("it.luzzetti.justdrink.backoffice.infrastructure.output.jpa")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
class RestaurantJpaRepositoryTest {

  /*
   * TODO:
   * Configurare PostGIS anziché PostgreSQL
   * static JdbcDatabaseContainer postgresqlContainer = new PostgisContainerProvider().newInstance();
   * postgresqlContainer.withUrlParam("serverTimezone", "UTC").withReuse(true).start();
   *
   * TODO:
   * Lanciare la validazione una sola volta. (Quando avremo configurato Liquibase)
   * https://vladmihalcea.com/validate-ddl-schema-spring-hibernate/
   *
   * Short ceremony
   * https://www.youtube.com/watch?v=zFkTA95w0oo&ab_channel=PhilipRiecks
   */

  @ServiceConnection
  static PostgreSQLContainer<?> postgresqlContainer =
      new PostgreSQLContainer<>("postgres:15.3-alpine");

  static {
    postgresqlContainer.withUrlParam("serverTimezone", "UTC").withReuse(true).start();
  }

  @Autowired private RestaurantJpaRepository restaurantJpaRepository;

  @Test
  @DisplayName("Meta - Context Is Loaded")
  @Order(1)
  void whenTestStarts_thenContextIsLoaded() {
    assertThat(restaurantJpaRepository).isNotNull();
  }

  @Test
  @DisplayName("Meta - Testcontainer is started")
  @Order(2)
  void whenTestStarts_thenTestcontainerIsRunning() {
    assertThat(postgresqlContainer.isRunning()).isTrue();
  }

  @Test
  @DisplayName("Save Restaurant - Happy Case")
  void whenSavingNewRestaurant_thenItWorks() {

    UUID theId = UUID.randomUUID();
    String theName = "TEST";

    RestaurantJpaEntity anEntity = craftValidRestaurantEntity(theId, theName);
    restaurantJpaRepository.save(anEntity);

    Optional<RestaurantJpaEntity> theFoundEntity =
        restaurantJpaRepository.findRestaurantByName(theName);

    assertThat(theFoundEntity).isNotEmpty();
    RestaurantJpaEntity theFoundRestaurant = theFoundEntity.get();
    assertThat(theFoundRestaurant).isNotNull();
    UUID anId = theFoundRestaurant.getId();
    assertEquals(theId, anId);
  }

  private static RestaurantJpaEntity craftValidRestaurantEntity(UUID theId, String theName) {

    OwnerJpaEmbeddable anOwner = new OwnerJpaEmbeddable();
    anOwner.setId(UUID.randomUUID());
    anOwner.setUsername("Christian");
    anOwner.setEmail("justdrink.admin@gmail.com");

    AddressJpaEmbeddable anAddress = new AddressJpaEmbeddable();
    anAddress.setDisplayName("Via Cairoli 1, 01100, Viterbo, Italia");

    RestaurantJpaEntity anEntity = new RestaurantJpaEntity();
    anEntity.setId(theId);
    anEntity.setName(theName);
    anEntity.setOwner(anOwner);
    anEntity.setAddress(anAddress);
    return anEntity;
  }
}
