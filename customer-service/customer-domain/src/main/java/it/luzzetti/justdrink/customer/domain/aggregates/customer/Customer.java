package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {

    private final CustomerId id;

    private String name;
    @Builder.Default
    private Set<Address> addressBook = new HashSet<>();


    public void addAddress(Address address) {
        this.addressBook.add(address);
    }

    // TODO: implementare un test !!
    public void removeAddress(AddressId addressId) {
        this.addressBook.removeIf(address -> Objects.equals(address.getId(), addressId));
    }


    public static CustomerBuilder builder() {
        return new CustomBuilder();
    }

    // Validations

    private static class CustomBuilder extends CustomerBuilder {

        /* Adding validations as part of build() method. */
        public Customer build() {

            Objects.requireNonNull(super.name, "Customer name cannot be null");

            return super.build();
        }
    }
}
