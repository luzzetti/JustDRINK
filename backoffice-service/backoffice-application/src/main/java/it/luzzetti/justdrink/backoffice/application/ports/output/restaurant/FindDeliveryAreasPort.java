package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.List;

public interface FindDeliveryAreasPort {

  public List<DeliveryArea> findDeliveryAreasContainingCoordinates(Coordinates coordinates);
}
