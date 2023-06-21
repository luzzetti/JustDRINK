package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.io.Serializable;
import java.util.UUID;

public record OwnerId(UUID id) implements Serializable {
  public static OwnerId from(UUID id) {
    return new OwnerId(id);
  }
}
