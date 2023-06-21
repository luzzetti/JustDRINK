package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record MenuId(UUID id) {
  public static MenuId from(UUID uuid) {
    return new MenuId(uuid);
  }
}
