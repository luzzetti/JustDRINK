package it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;

@Getter
@Builder
public class DeliveryArea {

  private RestaurantId restaurantId;
  private Polygon polygon;
}
