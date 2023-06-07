package it.luzzetti.justdrink.customer.application.service.customer;

import it.luzzetti.justdrink.customer.application.ports.input.customer.ChangeCustomerNameUseCase;
import it.luzzetti.justdrink.customer.application.ports.output.customer.FindCustomerByIdPort;
import it.luzzetti.justdrink.customer.application.ports.output.customer.SaveCustomerPort;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;
import it.luzzetti.justdrink.customer.domain.aggregates.customer.CustomerName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeCustomerNameApplicationService implements ChangeCustomerNameUseCase {

  private final FindCustomerByIdPort findCustomerByIdPort;
  private final SaveCustomerPort saveCustomerPort;

  @Override
  @Transactional
  public void changeCustomerName(@Valid ChangeCustomerNameCommand command) {

    CustomerName theCustomerName = command.customerName();
    Customer theCustomer = findCustomerByIdPort.findCustomerById(command.customerId());

    theCustomer.changeCustomerName(theCustomerName);

    saveCustomerPort.saveCustomer(theCustomer);
  }
}
