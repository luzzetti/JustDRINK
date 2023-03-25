package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.ValueObject;
import java.util.ArrayList;
import java.util.List;

@ValueObject
record MenuSection(String title, List<Product> products) {

  private MenuSection(String title) {
    this(title, new ArrayList<>());
  }

  public static MenuSection newMenuSection(String title) {
    return new MenuSection(title, new ArrayList<>());
  }

}
