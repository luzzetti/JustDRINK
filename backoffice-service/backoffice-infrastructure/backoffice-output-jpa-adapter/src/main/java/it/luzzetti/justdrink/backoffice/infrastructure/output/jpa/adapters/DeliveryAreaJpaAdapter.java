package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.commons.exceptions.ElementNotFoundException;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveDeliveryAreaPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.DeliveryAreaJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.DeliveryAreaJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class DeliveryAreaJpaAdapter implements SaveDeliveryAreaPort {

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final DeliveryAreaJpaRepository deliveryAreaJpaRepository;
  private final DeliveryAreaJpaMapper deliveryAreaJpaMapper;

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
}
