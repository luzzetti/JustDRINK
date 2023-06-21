package it.luzzetti.justdrink.order.application.ports.input;

import lombok.Builder;

public interface CreateOrderUseCase {

  void createOrder(CreateOrderCommand command);

  @Builder
  record CreateOrderCommand() {}
}
