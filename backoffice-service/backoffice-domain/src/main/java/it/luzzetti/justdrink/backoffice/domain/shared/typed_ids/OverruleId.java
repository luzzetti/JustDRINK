package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record OverruleId(UUID id) {

  public static OverruleId empty() {
    return new OverruleId(null);
  }

  public static OverruleId from(UUID uuid) {
    return new OverruleId(uuid);
  }
}
