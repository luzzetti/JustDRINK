package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;
import java.util.ArrayList;
import java.util.Collections;
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
  @Builder.Default
  private List<MenuSection> sections = new ArrayList<>();

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

  public void removeSectionById(MenuSectionId menuSectionIdToRemove) {
    // Validations ?
    sections.removeIf(s -> Objects.equals(s.getId(), menuSectionIdToRemove));
  }

  public List<MenuSection> getSections() {
    // Approfondire "Unmodifiable vs Immutable"
    return Collections.unmodifiableList(sections);
  }

  public MenuSection getLastCreatedSection() {
    return sections.stream()
        .max(Comparator.comparing(MenuSection::getCreatedAt))
        .orElseThrow(IllegalArgumentException::new);
  }

  public void addProductToSection(Product theNewProduct, MenuSectionId sectionId) {
    MenuSection theSection = getSectionByIdMandatory(sectionId);
    theSection.addProduct(theNewProduct);
  }

  private MenuSection getSectionByIdMandatory(MenuSectionId sectionId) {
    return sections.stream()
        .filter(o -> sectionId.equals(o.getId()))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("Non esiste nessuna sezione con questo id"));
  }

  public Product getLastCreatedProduct() {
    return sections.stream()
        .flatMap(o -> o.getProducts().stream())
        .max(Comparator.comparing(Product::getCreatedAt))
        .orElseThrow(() -> new IllegalArgumentException("Non ci sono prodotti"));
  }

  public List<Product> getProductsOfSection(MenuSectionId sectionId) {
    List<Product> products = getSectionByIdMandatory(sectionId).getProducts();

    return Collections.unmodifiableList(products);
  }

}
