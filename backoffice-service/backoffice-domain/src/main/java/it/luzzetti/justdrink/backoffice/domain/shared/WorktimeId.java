package it.luzzetti.justdrink.backoffice.domain.shared;

import java.util.UUID;

public record WorktimeId(UUID id) {
  public static WorktimeId empty() {
    return new WorktimeId(null);
  }

  public static WorktimeId from(UUID uuid) {
    return new WorktimeId(uuid);
  }
}
