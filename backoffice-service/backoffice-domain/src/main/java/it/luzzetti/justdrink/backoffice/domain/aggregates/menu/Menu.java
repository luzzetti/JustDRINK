package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import static it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection.newMenuSection;

import it.luzzetti.justdrink.backoffice.domain.shared.AggregateRoot;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@AggregateRoot
public class Menu {
  @Getter private final MenuId id = MenuId.empty();
  private final List<MenuSection> sections = new ArrayList<>();

  public static Menu newMenu() {
    return new Menu();
  }
  public Collection<MenuSection> getSections() {
    return Collections.unmodifiableList(sections);
  }

  public void addSection(String sectionTitle) {
    sections.add(newMenuSection(sectionTitle));
  }

}
