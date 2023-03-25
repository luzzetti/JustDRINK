package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static org.assertj.core.api.Assertions.assertThat;
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

  @Test
  @DisplayName("Add Section To Menu")
  void whenAddingMenuSection_thanItGetsAdded() {
    Menu menu = Menu.newMenu();

    menu.addSection("Pizze Rosse");

    assertThat(menu.getSections()).hasSize(1);
  }
}
