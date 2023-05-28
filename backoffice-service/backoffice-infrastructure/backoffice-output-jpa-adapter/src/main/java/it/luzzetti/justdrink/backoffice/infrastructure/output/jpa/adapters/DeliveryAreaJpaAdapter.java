package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area.GenerateDeliveryAreaIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveDeliveryAreaPort;
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
import org.geolatte.geom.Point;
import org.geolatte.geom.Position;
import org.geolatte.geom.PositionSequenceBuilder;
import org.geolatte.geom.PositionSequenceBuilders;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class DeliveryAreaJpaAdapter
    implements SaveDeliveryAreaPort, GenerateDeliveryAreaIdPort, FindDeliveryAreasPort {

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final DeliveryAreaJpaRepository deliveryAreaJpaRepository;
  private final DeliveryAreaJpaMapper deliveryAreaJpaMapper;
  private final CustomDeliveryAreaRepository customDeliveryAreaRepository;

  @Override
  public DeliveryArea saveDeliveryArea(DeliveryArea aNewDeliveryArea) {

    DeliveryAreaJpaEntity theDeliveryArea = deliveryAreaJpaMapper.toEntity(aNewDeliveryArea);

    RestaurantJpaEntity theAssociatedRestaurant =
        restaurantJpaRepository
            .findById(aNewDeliveryArea.getRestaurantId().id())
            .orElseThrow(() -> new ElementNotFoundException(RestaurantErrors.NOT_FOUND));

    theDeliveryArea.setRestaurant(theAssociatedRestaurant);

    DeliveryAreaJpaEntity theSavedEntity = deliveryAreaJpaRepository.save(theDeliveryArea);
    return deliveryAreaJpaMapper.toDomain(theSavedEntity);
  }

  @Override
  public List<DeliveryArea> findDeliveryAreasContainingCoordinates(Coordinates coordinates) {

//TODO non ho idea se la costruzione di questo point funziona
    CoordinateReferenceSystem<?> crs =
        CoordinateReferenceSystems.WGS84; // Sistema di riferimento delle coordinate

    Position position =
        new Position(
            coordinates.latitude().latitudeValue(), coordinates.longitude().longitudeValue()) {
          @Override
          public int getCoordinateDimension() {
            return 0;
          }
        };

    Point point = new Point(position, crs);

    List<DeliveryAreaJpaEntity> deliveryAreasEntity =
        customDeliveryAreaRepository.findByPoint(point);

    return deliveryAreasEntity.stream()
        .map(deliveryAreaJpaMapper::toDomain)
        .collect(Collectors.toList());
  }

}
