package it.luzzetti.justdrink.customer.application.service.customer;

import it.luzzetti.justdrink.customer.application.ports.input.customer.AddAddressToCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.output.customer.AddressIdGeneratorPort;
import it.luzzetti.justdrink.customer.application.ports.output.customer.FindCustomerByIdPort;
import it.luzzetti.justdrink.customer.application.ports.output.customer.SaveCustomerPort;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.AddressId;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddAddressToCustomerApplicationService implements AddAddressToCustomerUseCase {

    private final FindCustomerByIdPort findCustomerByIdPort;
    private final SaveCustomerPort saveCustomerPort;
    private final AddressIdGeneratorPort addressIdGeneratorPort;

    @Override
    @Transactional
    public Address addAddressToCustomer(@Valid AddAddressToCustomerCommand command) {
        // fetch data
        CustomerId customerId = command.customerId();
        Customer theCustomerToModify = findCustomerByIdPort.findCustomerById(customerId);

        // modify data
        AddressId addressId = addressIdGeneratorPort.generateAddressIdentifier();
        Address theNewAddress = Address.builder().id(addressId).name(command.address().getName()).build();

        // crafting
        theCustomerToModify.addAddress(theNewAddress);
        saveCustomerPort.saveCustomer(theCustomerToModify);
        return theNewAddress;
    }
}
