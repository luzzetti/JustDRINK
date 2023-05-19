package it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.DeliveryAreaId;

public interface GenerateDeliveryAreaIdPort {
  DeliveryAreaId nextDeliveryAreaIdentifier();
}
