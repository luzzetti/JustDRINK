package it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer;

import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase.CreateCustomerCommand;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto.CustomerResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  public record CustomerCreationRequest(@NotNull @NotBlank String name) {}
}
