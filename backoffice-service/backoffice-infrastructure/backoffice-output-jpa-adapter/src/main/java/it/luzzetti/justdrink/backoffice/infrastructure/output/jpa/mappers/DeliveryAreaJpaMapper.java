package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.DeliveryAreaId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.DeliveryAreaJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryAreaJpaMapper {

  DeliveryArea toDomain(DeliveryAreaJpaEntity anEntity);

  DeliveryAreaJpaEntity toEntity(DeliveryArea aMenu);

  default UUID map(DeliveryAreaId deliveryAreaId) {
    return deliveryAreaId.id();
  }

  default DeliveryAreaId map(UUID uuid) {
    return DeliveryAreaId.from(uuid);
  }
}
