package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record MenuId(UUID id) {

  public static MenuId empty() {
    return new MenuId(null);
  }

  public static MenuId from(UUID uuid) {
    return new MenuId(uuid);
  }
}
