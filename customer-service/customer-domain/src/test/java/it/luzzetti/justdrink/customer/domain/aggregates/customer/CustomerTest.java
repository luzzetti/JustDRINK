package it.luzzetti.justdrink.customer.domain.aggregates.customer;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.luzzetti.justdrink.customer.domain.aggregates.customer.Customer.CustomerBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

  private CustomerBuilder theCustomerBuilder;

  @BeforeEach
  void init() {
    theCustomerBuilder =
        Customer.builder()
            .id(CustomerId.from(UUID.randomUUID()))
            .customerName(CustomerName.of("Christian", "Luzzetti"));
  }

  @Test
  @DisplayName("Customer Creating - No name Error")
  void whenCreatingANewCustomerWithoutCustomerName_thenThrowsApplicationException() {
    theCustomerBuilder.customerName(null);
    assertThrows(Exception.class, theCustomerBuilder::build);
  }
}
