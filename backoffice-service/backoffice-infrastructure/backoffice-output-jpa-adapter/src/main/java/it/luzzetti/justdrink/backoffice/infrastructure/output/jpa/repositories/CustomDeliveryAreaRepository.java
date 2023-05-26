package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import java.awt.Point;
import java.util.List;

public interface CustomDeliveryAreaRepository {

  List<DeliveryAreaJpaEntity> findByPoint(Point point);
}
