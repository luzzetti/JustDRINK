package it.luzzetti.justdrink.customer.application.ports.input.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface CreateCustomerUseCase {
  Customer createCustomer(CreateCustomerCommand command);

  @Builder
  record CreateCustomerCommand() {}
}
