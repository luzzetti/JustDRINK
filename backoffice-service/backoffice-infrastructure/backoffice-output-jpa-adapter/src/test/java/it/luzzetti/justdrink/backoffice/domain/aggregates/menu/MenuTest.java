package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

  @Test
  @DisplayName("Menu Creation - Happy Case")
  void whenNewMenuIsCreated_thanItsIdIsNull() {
    Menu menu = Menu.newMenu();

    Assertions.assertNotNull(menu);
    assertNotNull(menu.getId());

    assertNull(menu.getId().id());
  }
}
