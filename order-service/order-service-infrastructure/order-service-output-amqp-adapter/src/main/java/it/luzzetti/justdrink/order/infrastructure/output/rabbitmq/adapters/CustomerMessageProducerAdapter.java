package it.luzzetti.justdrink.order.infrastructure.output.rabbitmq.adapters;

import it.luzzetti.justdrink.order.application.ports.output.CustomerCommandPort;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomerMessageProducerAdapter implements CustomerCommandPort {

  private static final String CHECKOUT_SAGA_EXCHANGE = "CHECKOUT-SAGA";
  private static final String VALIDATE_CUSTOMER_ROUTING_KEY = "customer.validate.command";
  private static final String VALIDATED_CUSTOMER_REPLY = "customer.validated.reply";

  private final RabbitTemplate template;

  @Override
  public void validateCustomer(OrderId theOrderId, CustomerId theCustomerId) {

    MessagePostProcessor postProcessor =
        message -> {
          message.getMessageProperties().setReplyTo(VALIDATED_CUSTOMER_REPLY);
          return message;
        };

    var command =
        ValidateCustomerCommand.builder().orderId(theOrderId).customerId(theCustomerId).build();

    template.convertAndSend(
        CHECKOUT_SAGA_EXCHANGE, VALIDATE_CUSTOMER_ROUTING_KEY, command, postProcessor);
  }

  @Builder
  public record ValidateCustomerCommand(OrderId orderId, CustomerId customerId) {}
}
