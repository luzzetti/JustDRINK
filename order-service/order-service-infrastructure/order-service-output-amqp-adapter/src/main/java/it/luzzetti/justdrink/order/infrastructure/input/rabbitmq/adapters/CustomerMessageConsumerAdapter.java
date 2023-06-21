package it.luzzetti.justdrink.order.infrastructure.input.rabbitmq.adapters;

import it.luzzetti.justdrink.order.application.ports.input.OrchestrateCheckoutSaga;
import it.luzzetti.justdrink.order.application.ports.input.OrchestrateCheckoutSaga.EndCheckoutSagaCommand;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomerMessageConsumerAdapter {

  private final OrchestrateCheckoutSaga orchestrateCheckoutSaga;
  private static final String VALIDATED_CUSTOMER_REPLY = "customer.validated.reply";

  @RabbitListener(queues = VALIDATED_CUSTOMER_REPLY)
  public void validatedCustomerHandler(ValidatedCustomerReply reply) {

    var command = EndCheckoutSagaCommand.builder().orderId(reply.orderId()).build();

    orchestrateCheckoutSaga.endCheckoutSaga(command);
  }

  public record ValidatedCustomerReply(
      OrderId orderId, CustomerId customerId, Boolean isValidated) {}
}
