package it.luzzetti.justdrink.customer.application.ports.output.customer;

import it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer;

public interface SaveCustomerPort {
  Customer saveCustomer(Customer aNewCustomer);
}
