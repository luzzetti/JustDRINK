package it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer;

import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase;
import it.luzzetti.justdrink.customer.application.ports.input.customer.CreateCustomerUseCase.CreateCustomerCommand;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Address;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.infrastructure.input.rest.adapters.customer.dto.CustomerResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;
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
      @RequestBody CustomerCreationRequest request) {

    var command = CreateCustomerCommand.builder().name(request.name()).build();

    Customer theCreatedCustomer = createCustomerUseCase.createCustomer(command);

    return ResponseEntity.ok(CustomerResource.builder().name(theCreatedCustomer.getName()).build());
  }

  record CustomerCreationRequest(@NotNull @NotBlank String name) {}
}
