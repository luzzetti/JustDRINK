package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import java.util.ArrayList;
import java.util.List;

record MenuSection(String title, List<Product> products) {

  public static MenuSection newMenuSection(String title) {
    return new MenuSection(title, new ArrayList<>());
  }
}
