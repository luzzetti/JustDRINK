package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;

public interface SaveDeliveryAreaPort {
  DeliveryArea saveDeliveryArea(DeliveryArea aNewDeliveryArea);
}
