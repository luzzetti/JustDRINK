package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DeliveryAreaJpaMapper {

  @Mapping(target = "restaurantId", source = "restaurant.id")
  DeliveryArea toDomain(DeliveryAreaJpaEntity anEntity);

  @Mapping(target = "id", source = "restaurantId.id")
  DeliveryAreaJpaEntity toEntity(DeliveryArea aMenu);

  default UUID map(RestaurantId restaurantId) {
    return restaurantId.id();
  }

  default RestaurantId map(UUID uuid) {
    return RestaurantId.from(uuid);
  }
}
