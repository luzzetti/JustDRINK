package it.luzzetti.justdrink.backoffice.domain.aggregates.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuSection {

  @Builder.Default private MenuSectionId id = MenuSectionId.empty();
  private String title;
  @Builder.Default private Instant createdAt = Instant.now();

  public static MenuSection newMenuSection(String title) {
    return MenuSection.builder().title(title).build();
  }
}
