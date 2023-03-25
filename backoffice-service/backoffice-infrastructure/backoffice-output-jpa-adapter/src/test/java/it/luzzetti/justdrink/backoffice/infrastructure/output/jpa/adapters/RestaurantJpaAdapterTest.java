package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import com.querydsl.jpa.impl.JPAQueryFactory;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.GenerateRestaurants;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.SpringDataConfiguration;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.QRestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
  private JPAQueryFactory queryFactory;
  @Autowired RestaurantJpaRepository restaurantJpaRepository;
  @Autowired RestaurantJpaMapper restaurantJpaMapper;

  @BeforeAll
  void beforeAll() {
    List<RestaurantJpaEntity> generated =
        GenerateRestaurants.generateRestaurants().stream()
            .map(restaurantJpaMapper::toEntity)
            .toList();

    restaurantJpaRepository.saveAll(generated);
  }

  @BeforeEach
  void beforeEach() {
    queryFactory = new JPAQueryFactory(entityManager);
  }

  @AfterAll
  void afterAll() {
    restaurantJpaRepository.deleteAll();
  }

  @Test
  void should_find_no_tutorials_if_repository_is_empty() {
    QRestaurantJpaEntity restaurant = QRestaurantJpaEntity.restaurantJpaEntity;

    RestaurantJpaEntity found =
        queryFactory
            .selectFrom(restaurant)
            .where(restaurant.name.equalsIgnoreCase("Tana del cibo"))
            .fetchOne();

    System.out.println(found.getName());
  }
}
