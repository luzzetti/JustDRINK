package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OwnerId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OwnerJpaEmbeddable;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface OwnerJpaMapper {
  Owner toDomain(OwnerJpaEmbeddable anEntity);

  OwnerJpaEmbeddable toEntity(Owner domain);

  default UUID map(OwnerId aTypedId) {
    return aTypedId.id();
  }

  default OwnerId map(UUID uuid) {
    return OwnerId.from(uuid);
  }
}
