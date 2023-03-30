package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
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

  public static MenuSection newMenuSection(String title) {
    return MenuSection.builder().title(title).build();
  }

  public void addProduct(Product product) {
    if (containsProductWithName(product.getName())) {
      throw new IllegalArgumentException("Esiste già un prodotto con questo nome");
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
            () -> new IllegalArgumentException("Non esiste nessun prodotto con questo id"));
  }
}
