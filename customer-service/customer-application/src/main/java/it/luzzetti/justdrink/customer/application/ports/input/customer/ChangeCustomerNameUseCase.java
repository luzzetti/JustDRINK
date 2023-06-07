package it.luzzetti.justdrink.customer.application.ports.input.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.CustomerName;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface ChangeCustomerNameUseCase {
  void changeCustomerName(ChangeCustomerNameCommand command);

  @Builder
  record ChangeCustomerNameCommand(
      @NotNull CustomerId customerId, @NotNull CustomerName customerName) {}
}
