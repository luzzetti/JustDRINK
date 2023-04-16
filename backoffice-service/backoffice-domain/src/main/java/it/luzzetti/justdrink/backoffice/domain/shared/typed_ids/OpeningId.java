package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record OpeningId(UUID id) {

  public static OpeningId empty() {
    return new OpeningId(null);
  }

  public static OpeningId from(UUID uuid) {
    return new OpeningId(uuid);
  }
}
