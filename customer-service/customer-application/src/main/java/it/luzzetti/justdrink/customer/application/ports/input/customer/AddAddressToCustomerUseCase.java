package it.luzzetti.justdrink.customer.application.ports.input.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface AddAddressToCustomerUseCase {

    Address addAddressToCustomer(AddAddressToCustomerCommand command);

    @Builder
    record AddAddressToCustomerCommand(@NotNull CustomerId customerId, @NotNull Address address) {
    }
}
