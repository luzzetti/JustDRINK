package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.geolatte.geom.Point;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class CustomDeliveryAreaRepositoryImpl implements CustomDeliveryAreaRepository {

  private final EntityManager entityManager;

  @Override
  public List<DeliveryAreaJpaEntity> findByPoint(Point point) {

    String queryString =
        "SELECT d FROM DeliveryAreaJpaEntity d WHERE contains(d.polygon, :point) = true";
    Query query = entityManager.createQuery(queryString, DeliveryAreaJpaEntity.class);
    query.setParameter("point", point);

    List<DeliveryAreaJpaEntity> resultList = query.getResultList();

    log.warn(() -> String.format("TEST aree con poligoni che contengono un punto %", resultList));

    return resultList;
  }
}
