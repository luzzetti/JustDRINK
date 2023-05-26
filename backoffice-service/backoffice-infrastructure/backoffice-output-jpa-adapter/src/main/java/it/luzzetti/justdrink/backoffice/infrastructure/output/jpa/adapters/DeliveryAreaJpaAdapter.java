package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area.GenerateDeliveryAreaIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveDeliveryAreaPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.restaurant.FindDeliveryAreasPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.DeliveryAreaId;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.DeliveryAreaJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.CustomDeliveryAreaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.DeliveryAreaJpaRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class DeliveryAreaJpaAdapter
    implements SaveDeliveryAreaPort, GenerateDeliveryAreaIdPort, FindDeliveryAreasPort {

  private final DeliveryAreaJpaRepository deliveryAreaJpaRepository;
  private final DeliveryAreaJpaMapper deliveryAreaJpaMapper;
  private final CustomDeliveryAreaRepository customDeliveryAreaRepository;

  @Override
  public DeliveryAreaId nextDeliveryAreaIdentifier() {
    return DeliveryAreaId.from(UUID.randomUUID());
  }

  @Override
  public DeliveryArea saveDeliveryArea(DeliveryArea aNewDeliveryArea) {
    DeliveryAreaJpaEntity theEntity = deliveryAreaJpaMapper.toEntity(aNewDeliveryArea);
    DeliveryAreaJpaEntity theSavedEntity = deliveryAreaJpaRepository.save(theEntity);
    return deliveryAreaJpaMapper.toDomain(theSavedEntity);
  }

  @Override
  public List<DeliveryArea> findDeliveryAreasByClientAdress(Coordinates coordinates) {

    Point point =
        new Point(coordinates.latitude().latitudeValue(), coordinates.longitude().longitudeValue());

    List<DeliveryAreaJpaEntity> deliveryAreasEntity =
        customDeliveryAreaRepository.findByPoint(point);

    return deliveryAreasEntity.stream()
        .map(deliveryAreaJpaMapper::toDomain)
        .collect(Collectors.toList());
  }
}
