package it.luzzetti.justdrink.customer.application.service.customer;

import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.output.customer.CustomerIdGeneratorPort;
import it.luzzetti.justdrink.customer.application.ports.output.customer.SaveCustomerPort;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.CustomerName;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateCustomerApplicationService implements CreateCustomerUseCase {

  private final SaveCustomerPort saveCustomerPort;
  private final CustomerIdGeneratorPort customerIdGeneratorPort;

  @Override
  @Transactional
  public Customer createCustomer(@Valid CreateCustomerCommand command) {
    // Fetching resources
    CustomerId generatedId = customerIdGeneratorPort.generateCustomerIdentifier();

    // Calling useCase
    Customer aNewCustomer =
        Customer.builder()
            .id(generatedId)
            .customerName(CustomerName.newAnonymousCustomer())
            .build();

    // Crafting response
    return saveCustomerPort.saveCustomer(aNewCustomer);
  }
}
