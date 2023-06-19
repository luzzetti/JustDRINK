package it.luzzetti.justdrink.order.application.ports.input;

import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface OrchestrateCheckoutSaga {

  void startCheckoutSaga(StartCheckoutSagaCommand command);

  void endCheckoutSaga(EndCheckoutSagaCommand command);

  @Builder
  record StartCheckoutSagaCommand(@NotNull OrderId orderId) {}

  @Builder
  record EndCheckoutSagaCommand(@NotNull OrderId orderId) {}
}
