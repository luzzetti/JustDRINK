package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
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
  @Builder.Default private Instant createdAt = Instant.now();

  @Builder.Default private List<Product> products = new ArrayList<>();

  // static factory method //of from (generiche) now (LocalDate che poi userà of)
  // se lo vuoi fare da solo lo chiami come questo sotto

  // ogni aggregato ha un entity principale chiamata RootEntity

  public static MenuSection newMenuSection(String title) {
    return MenuSection.builder().title(title).build();
  }

  // aggiungi prodotto!

  public void addProduct(Product product) {
    if (containsProductWithName(product.getName())) {
      throw new IllegalArgumentException("Esiste già un prodotto con questo nome");
    }
    products.add(product);
  }

  private boolean containsProductWithName(String name) {
    return products.stream().anyMatch(product -> Objects.equals(name, product.getName()));
  }
}
