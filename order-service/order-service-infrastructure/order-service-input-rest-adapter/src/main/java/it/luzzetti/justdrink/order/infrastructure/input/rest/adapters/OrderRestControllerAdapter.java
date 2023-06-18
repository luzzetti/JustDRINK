package it.luzzetti.justdrink.order.infrastructure.input.rest.adapters;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/orders")
@Log4j2
@RequiredArgsConstructor
public class OrderRestControllerAdapter {

  // UseCases

  // Queries

  // Mappers

  @PostMapping
  public void createOrder() {}

  @PostMapping("{orderId}:checkout")
  public void checkoutOrder(@PathVariable UUID orderId) {}

  @GetMapping("{orderId}")
  public void showOrder(@PathVariable UUID orderId) {}

  @GetMapping
  public void listOrders() {}
}
