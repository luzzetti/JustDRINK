package it.luzzetti.justdrink.customer.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.customer.application.ports.output.customer.CustomerIdGeneratorPort;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.customer.infrastructure.output.jpa.entities.customer.CustomerJpaEntity;
import it.luzzetti.justdrink.customer.infrastructure.output.jpa.mappers.customer.CustomerJpaMapper;
import it.luzzetti.justdrink.customer.infrastructure.output.jpa.repositories.CustomerJpaRepository;
import it.luzzetti.justdrink.customer.application.ports.output.customer.SaveCustomerPort;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomerJpaAdapter implements SaveCustomerPort, CustomerIdGeneratorPort {

  private final CustomerJpaRepository customerJpaRepository;
  private final CustomerJpaMapper customerJpaMapper;

  @Override
  public Customer saveCustomer(Customer aNewCustomer) {
    CustomerJpaEntity aNewEntity = customerJpaMapper.toEntity(aNewCustomer);
    CustomerJpaEntity savedEntity = customerJpaRepository.save(aNewEntity);

    return customerJpaMapper.toDomain(savedEntity);
  }

  @Override
  public CustomerId generateCustomerIdentifier() {
    return CustomerId.from(UUID.randomUUID());
  }
}
