package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.spatial.Spatial;
import org.hibernate.spatial.contributor.SpatialFunctionContributor;
import org.springframework.stereotype.Repository;

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

    return null;
  }
}
