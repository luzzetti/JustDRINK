package it.luzzetti.justdrink.customer.infrastructure.output.jpa.mappers.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.CustomerName;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer.CustomerJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {AddressJpaMapper.class})
public interface CustomerJpaMapper {

  @Mapping(target = "customerName", source = ".")
  Customer toDomain(CustomerJpaEntity theCreatedEntity);

 default CustomerName toCustomerName(CustomerJpaEntity entity){
    return CustomerName.of(entity.getFirstName(), entity.getLastName());
}

  @Mapping(source = "customerName.firstName", target = "firstName")
  @Mapping(source = "customerName.lastName", target = "lastName")
  CustomerJpaEntity toEntity(Customer aNewCustomer);

  default UUID map(CustomerId customerId) {
    return customerId.id();
  }

  default CustomerId map(UUID uuid) {
    return CustomerId.from(uuid);
  }
}
