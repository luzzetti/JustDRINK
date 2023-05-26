package it.luzzetti.justdrink.customer.application.ports.input.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;

public interface CreateCustomerUseCase {
  Customer createCustomer(CreateCustomerCommand command);

  @Builder
  record CreateCustomerCommand(@NotNull @NotEmpty String name, Set<Address> addressBook) {}
}
