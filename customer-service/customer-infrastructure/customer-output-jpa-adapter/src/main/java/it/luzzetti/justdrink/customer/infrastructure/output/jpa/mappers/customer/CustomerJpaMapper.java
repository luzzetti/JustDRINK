package it.luzzetti.justdrink.customer.infrastructure.output.jpa.mappers.customer;

import it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer.CustomerJpaEntity;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {AddressJpaMapper.class})
public interface CustomerJpaMapper {

  Customer toDomain(CustomerJpaEntity theCreatedEntity);

  CustomerJpaEntity toEntity(Customer aNewCustomer);

  default UUID map(CustomerId customerId) {
    return customerId.id();
  }

  default CustomerId map(UUID uuid) {
    return CustomerId.from(uuid);
  }
}
