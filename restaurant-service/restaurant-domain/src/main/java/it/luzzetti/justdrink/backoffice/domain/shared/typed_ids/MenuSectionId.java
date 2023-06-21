package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record MenuSectionId(UUID id) {
  public static MenuSectionId empty() {
    return new MenuSectionId(null);
  }

  public static MenuSectionId from(UUID uuid) {
    return new MenuSectionId(uuid);
  }
}
