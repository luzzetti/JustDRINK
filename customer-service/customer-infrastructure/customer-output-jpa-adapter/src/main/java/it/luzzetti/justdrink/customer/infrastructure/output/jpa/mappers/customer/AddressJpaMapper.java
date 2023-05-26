package it.luzzetti.justdrink.customer.infrastructure.output.jpa.mappers.customer;

import it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer.AddressJpaEntity;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface AddressJpaMapper {
  Address toDomain(AddressJpaEntity theCreatedEntity);

  AddressJpaEntity toEntity(Address aNewAddress);

  default UUID map(AddressId addressId) {
    return addressId.id();
  }

  default AddressId map(UUID uuid) {
    return AddressId.from(uuid);
  }

}
