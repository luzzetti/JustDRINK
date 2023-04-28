package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.MenuJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.MenuJpaMapperImpl;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.MenuSectionJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.MenuJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/*
 * In this example, we have a test class called `JpaAdapterTest` that uses Mockito to mock our `SampleJpaEntityRepository` interface. We then set up our `JpaAdapter` instance with the mocked repository in the `setup` method.
 *
 * The `givenSampleEntity_thenShouldReturnSampleModel` test method creates a `SampleJpaEntity` instance, sets its `id` and `name`, then uses Mockito's `when` method to mock the `findById` method of our repository interface to return the created entity when called with `1L`. We then call the `findById` method on our `JpaAdapter` instance and pass in `1L`, saving the result to `sample`. Finally, we use `assertThat` from the `assertj` library to check if the returned `Sample` instance has the same `id` and `name` as the `SampleJpaEntity` we created.
 *
 * The `givenSampleModel_thenShouldSaveSampleEntity` test method creates a new instance of `Sample`, sets its `name`, then calls the `save` method on our `JpaAdapter` instance, passing in the `Sample` instance. Finally, we use Mockito's `verify` method to check if the `save` method of our `SampleJpaEntityRepository` interface was called once with any `SampleJpaEntity` instance.
 */

class MenuJpaAdapterTest {

  private MenuJpaAdapter adapterUnderTest;

  @Mock private MenuJpaRepository menuJpaRepository;
  @Mock private RestaurantJpaRepository restaurantJpaRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // Al momento, inizializzo i mapper composti così:
    // https://github.com/mapstruct/mapstruct/issues/1261

    MenuJpaMapper menuJpaMapper = new MenuJpaMapperImpl();
    ReflectionTestUtils.setField(
        menuJpaMapper, "restaurantJpaMapper", Mappers.getMapper(RestaurantJpaMapper.class));
    ReflectionTestUtils.setField(
        menuJpaMapper, "menuSectionJpaMapper", Mappers.getMapper(MenuSectionJpaMapper.class));

    adapterUnderTest =
        new MenuJpaAdapter(menuJpaRepository, restaurantJpaRepository, menuJpaMapper);
  }

  @Test
  void givenSampleEntity_thenShouldReturnSampleModel() {
    // given
    MenuJpaEntity entity = new MenuJpaEntity();
    UUID theMenuId = UUID.randomUUID();
    RestaurantId theRestaurantId = RestaurantId.from(UUID.randomUUID());
    entity.setId(theMenuId);
    entity.setSections(Collections.emptySet());

    // Importante!!!
    when(menuJpaRepository.findMenuByRestaurantId(theRestaurantId.id()))
        .thenReturn(Optional.of(entity));

    // when
    Menu sample = adapterUnderTest.findMenuByRestaurantIdMandatory(theRestaurantId);

    // then
    verify(menuJpaRepository, times(1)).findMenuByRestaurantId(theRestaurantId.id());

    assertThat(sample.getId().id()).isEqualTo(entity.getId());
    //    assertThat(sample.getName()).isEqualTo(entity.getName());
  }

  @Test
  void givenSampleModel_thenShouldSaveSampleEntity() {
    // given
    Menu aMenuToSave =
        Menu.builder()
            .id(MenuId.from(UUID.randomUUID()))
            .restaurantId(RestaurantId.from(UUID.randomUUID()))
            .build();

    // when
    adapterUnderTest.saveMenu(aMenuToSave);

    // then
    verify(menuJpaRepository, times(1)).save(any(MenuJpaEntity.class));
  }
}
