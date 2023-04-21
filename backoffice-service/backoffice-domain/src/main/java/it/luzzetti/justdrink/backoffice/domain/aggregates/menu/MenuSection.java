package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ElementNotUniqueException;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuSection {

  @Builder.Default private MenuSectionId id = MenuSectionId.empty();
  private String title;
  @Builder.Default private List<Product> products = new ArrayList<>();

  @Builder.Default private Instant createdAt = Instant.now();

  public void addProduct(Product product) {
    if (containsProductWithName(product.getName())) {
      throw new ElementNotUniqueException(MenuErrors.PRODUCT_NAME_EXISTING);
    }
    products.add(product);
  }

  private boolean containsProductWithName(String name) {
    return products.stream().anyMatch(product -> Objects.equals(name, product.getName()));
  }

  public Product getProductById(ProductId productId) {
    return products.stream()
        .filter(product -> product.getId().equals(productId))
        .findFirst()
        .orElseThrow(
            () -> new ElementNotFoundException(MenuErrors.PRODUCT_NOT_FOUND));
  }

  public void removeProductById(ProductId productIdToRemove) {
    // Eseguire validazioni se necessario.
    products.removeIf(p -> Objects.equals(p.getId(), productIdToRemove));
  }

  // Syntactic sugar
  public void removeProduct(Product aProduct) {
    this.removeProductById(aProduct.getId());
  }
}
