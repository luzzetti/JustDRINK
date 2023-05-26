package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import java.util.List;
import org.geolatte.geom.Point;

public interface CustomDeliveryAreaRepository {

  List<DeliveryAreaJpaEntity> findByPoint(Point point);
}
