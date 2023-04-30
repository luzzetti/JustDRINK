package it.luzzetti.justdrink.backoffice.domain.shared.typed_ids;

import java.util.UUID;

public record DeliveryAreaId(UUID id) {
  public static DeliveryAreaId from(UUID uuid) {
    return new DeliveryAreaId(uuid);
  }
}
