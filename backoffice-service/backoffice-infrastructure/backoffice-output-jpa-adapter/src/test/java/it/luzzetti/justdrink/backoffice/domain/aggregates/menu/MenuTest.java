package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static org.junit.jupiter.api.Assertions.*;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

  @Test
  @DisplayName("Menu Creation - Happy Case")
  void whenNewValidMenuIsCreated_thanItWorks() {
    Menu menu =
        Menu.builder()
            .id(MenuId.from(UUID.randomUUID()))
            .restaurantId(RestaurantId.from(UUID.randomUUID()))
            .build();

    Assertions.assertNotNull(menu);
    assertNotNull(menu.getId());
    assertNotNull(menu.getId().id());
  }
}
