package it.luzzetti.justdrink.order.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.order.application.ports.input.CreateOrderUseCase;
import it.luzzetti.justdrink.order.application.ports.input.CreateOrderUseCase.CreateOrderCommand;
import it.luzzetti.justdrink.order.application.ports.input.OrchestrateCheckoutSaga;
import it.luzzetti.justdrink.order.application.ports.input.OrchestrateCheckoutSaga.StartCheckoutSagaCommand;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
  private final OrchestrateCheckoutSaga orchestrateCheckoutSaga;
  private final CreateOrderUseCase createOrderUseCase;

  // Queries

  // Mappers

  @PostMapping
  public ResponseEntity<Void> createOrder() {

    // TODO: This is just a test
    var command = CreateOrderCommand.builder().build();
    createOrderUseCase.createOrder(command);

    return ResponseEntity.ok().build();
  }

  @PostMapping("{orderId}:checkout")
  public ResponseEntity<Void> checkoutOrder(@PathVariable UUID orderId) {
    var command = StartCheckoutSagaCommand.builder().orderId(OrderId.from(orderId)).build();

    orchestrateCheckoutSaga.startCheckoutSaga(command);

    return ResponseEntity.accepted().build();
  }

  @GetMapping("{orderId}")
  public void showOrder(@PathVariable UUID orderId) {}

  @GetMapping
  public void listOrders() {}
}
