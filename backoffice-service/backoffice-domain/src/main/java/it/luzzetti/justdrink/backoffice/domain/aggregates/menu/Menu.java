package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotValidException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.util.ArrayList;
import java.util.Collections;
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

  public void addSection(MenuSection aMenuSection) {
    // Validations
    sections.add(aMenuSection);
  }

  public void removeSectionById(MenuSectionId menuSectionIdToRemove) {
    // Validations ?
    sections.removeIf(s -> Objects.equals(s.getId(), menuSectionIdToRemove));
  }

  public List<MenuSection> getSections() {
    // Approfondire "Unmodifiable vs Immutable"
    return Collections.unmodifiableList(sections);
  }

  public void addProductToSection(Product theNewProduct, MenuSectionId sectionId) {
    MenuSection theSection = getSectionByIdMandatory(sectionId);
    theSection.addProduct(theNewProduct);
  }

  public MenuSection getSectionByIdMandatory(MenuSectionId sectionId) {
    return sections.stream()
        .filter(o -> sectionId.equals(o.getId()))
        .findFirst()
        .orElseThrow(
            () -> new ElementNotFoundException(MenuErrors.SECTION_NOT_FOUND_ON_MENU));
  }

  public List<Product> listProductsFromSection(MenuSectionId sectionId) {
    List<Product> products = getSectionByIdMandatory(sectionId).getProducts();

    return Collections.unmodifiableList(products);
  }

  public Product getProductFromSection(MenuSectionId sectionId, ProductId productId) {
    MenuSection sectionByIdMandatory = getSectionByIdMandatory(sectionId);
    return sectionByIdMandatory.getProductById(productId);
  }

  public void removeProductFromSection(MenuSectionId sectionId, ProductId productId) {
    MenuSection sectionByIdMandatory = getSectionByIdMandatory(sectionId);
    sectionByIdMandatory.removeProductById(productId);
  }

  // Lombok's Builder Override

  public static MenuBuilder builder() {
    return new Menu.CustomBuilder();
  }

  public void moveProductBetweenSections(
      ProductId productId, MenuSectionId sourceSectionId, MenuSectionId targetSectionId) {

    Product theProductToBeMoved = getProductFromSection(sourceSectionId, productId);

    this.getSectionByIdMandatory(sourceSectionId).removeProduct(theProductToBeMoved);
    this.getSectionByIdMandatory(targetSectionId).addProduct(theProductToBeMoved);
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends MenuBuilder {

    /* Adding validations as part of build() method. */
    public Menu build() {

      if (super.id == null || super.id.id() == null) {
        throw new ElementNotValidException(MenuErrors.MENU_ID_REQUIRED);
      }

      return super.build();
    }
  }
}
