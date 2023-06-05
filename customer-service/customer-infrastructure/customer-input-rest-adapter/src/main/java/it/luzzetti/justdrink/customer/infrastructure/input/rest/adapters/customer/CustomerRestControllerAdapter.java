package it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer;

import io.swagger.v3.oas.annotations.Operation;
import it.luzzetti.justdrink.customer.application.ports.input.customer.AddAddressToCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase.CreateCustomerCommand;
import it.luzzetti.justdrink.customer.application.ports.input.customer.AddAddressToCustomerUseCase.AddAddressToCustomerCommand;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto.AddressResource;
import it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto.CustomerResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/customers")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class CustomerRestControllerAdapter {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final AddAddressToCustomerUseCase addAddressToCustomerUseCase;

    @PostMapping
    public ResponseEntity<CustomerResource> createCustomer(
            @RequestBody @Valid CustomerCreationRequest request) {

        // Fetching resources
        var command = CreateCustomerCommand.builder().name(request.name()).build();

        // Calling UseCase
        Customer theCreatedCustomer = createCustomerUseCase.createCustomer(command);

        // Crafting a Response
        CustomerResource theResponse =
                CustomerResource.builder().name(theCreatedCustomer.getName()).build();
        return ResponseEntity.ok(theResponse);
    }

    @Operation(summary = "Permette l'aggiunta di un indirizzo all'utente")
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<AddressResource> addAddressToCustomer(@PathVariable UUID customerId,
                                                                @RequestBody @Valid AddressAdditionToCustomerRequest request) {
        // Parsing parameters from HTTP
        var command = AddAddressToCustomerCommand.builder().customerId(CustomerId.from(customerId)).address(Address.builder().name(request.name).build()).build();

        // Calling Use-Case
        Address theAddress = addAddressToCustomerUseCase.addAddressToCustomer(command);

        // Crafting a HATEOAS response
        var resource = AddressResource.builder().name(theAddress.getName()).build();

        return ResponseEntity.ok(resource);
    }

    public record CustomerCreationRequest(@NotNull @NotBlank @Size(max = 100) String name) {
    }

    public record AddressAdditionToCustomerRequest(@NotNull @NotBlank @Size(max = 100) String name) {
    }
}
