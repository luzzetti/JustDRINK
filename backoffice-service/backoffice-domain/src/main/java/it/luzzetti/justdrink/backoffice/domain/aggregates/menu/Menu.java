package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Menu {

  private MenuId id;
  private RestaurantId restaurantId;
  @Builder.Default private List<MenuSection> sections = new ArrayList<>();

  // Non sono sicuro che debba essere possibile creare un menu senza associarlo ad un ristorante
  public static Menu newMenu() {
    return Menu.builder().restaurantId(RestaurantId.empty()).id(MenuId.empty()).build();
  }

  public static Menu newMenuForRestaurant(RestaurantId restaurantId) {
    return Menu.builder().restaurantId(restaurantId).id(MenuId.empty()).build();
  }

  public void createSection(String sectionTitle) {
    MenuSection aNewMenuSection = MenuSection.newMenuSection(sectionTitle);
    // Validations
    sections.add(aNewMenuSection);
  }

  public void removeSection(MenuSectionId menuSectionIdToRemove) {
    // Validations ?
    sections.removeIf(s -> Objects.equals(s.getId(), menuSectionIdToRemove));
  }

  public MenuSection getLastCreatedSection() {
    return sections.stream()
        .max(Comparator.comparing(MenuSection::getCreatedAt))
        .orElseThrow(IllegalArgumentException::new);
  }
}
