package it.luzzetti.justdrink.customer.infrastructure.output.rabbitmq.adapters;

import it.luzzetti.justdrink.customer.domain.shared.typed_ids.CustomerId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomerCommandConsumerListener {

  public static final String VALIDATE_CUSTOMERS_QUEUE = "customer.validate.command";

  @RabbitListener(queues = VALIDATE_CUSTOMERS_QUEUE)
  public ValidatedCustomerReply validateCustomerHandler(ValidateCustomerCommand command) {
    log.warn(() -> "Received a validate command: %s".formatted(command));

    ValidatedCustomerReply reply =
        new ValidatedCustomerReply(command.orderId(), command.customerId(), Boolean.TRUE);

    log.warn(() -> "Sending back a validated reply: %s".formatted(reply));
    return reply;
  }

  public record OrderId(UUID id) {}

  public record ValidateCustomerCommand(OrderId orderId, CustomerId customerId) {}

  public record ValidatedCustomerReply(
      OrderId orderId, CustomerId customerId, Boolean isValidated) {}
}
