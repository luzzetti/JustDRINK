package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuSectionTest {

  // TODO: Caricare la lista di nomi da un csv?

  @ParameterizedTest
  @ValueSource(strings = {"Pizze", "pizze"})
  @DisplayName("MenuSection Creation - Happy Case")
  void whenNewMenuIsCreated_thanItWorks(String sectionName) {
    MenuSection menuSection = MenuSection.newMenuSection(sectionName);
    assertNotNull(menuSection);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Pizze", "pizze"})
  @DisplayName("MenuSection Equality")
  void whenCreatingMenuSectionsWithSameName_thanAreEquals(String sectionName) {

    MenuSection aFirstMenuSection = MenuSection.newMenuSection(sectionName);
    MenuSection aSecondMenuSection = MenuSection.newMenuSection(sectionName);

    assertEquals(aFirstMenuSection, aSecondMenuSection);
  }
}
