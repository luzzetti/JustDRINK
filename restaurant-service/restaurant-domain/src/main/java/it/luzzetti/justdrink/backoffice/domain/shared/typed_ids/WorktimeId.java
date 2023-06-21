package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record WorktimeId(UUID id) {
  public static WorktimeId from(UUID uuid) {
    return new WorktimeId(uuid);
  }
}
