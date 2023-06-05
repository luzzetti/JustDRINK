package it.luzzetti.justdrink.customer.application.ports.output.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;

public interface AddressIdGeneratorPort {
    AddressId generateAddressIdentifier();
}
