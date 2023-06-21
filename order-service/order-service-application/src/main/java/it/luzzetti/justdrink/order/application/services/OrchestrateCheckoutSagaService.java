package it.luzzetti.justdrink.order.application.services;

import it.luzzetti.justdrink.order.application.ports.input.OrchestrateCheckoutSaga;
import it.luzzetti.justdrink.order.application.ports.output.CustomerCommandPort;
import it.luzzetti.justdrink.order.application.ports.output.OrderPersistencePort;
import it.luzzetti.justdrink.order.domain.aggregates.order.Order;
import it.luzzetti.justdrink.order.domain.aggregates.order.OrderStatus;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.CustomerId;
import it.luzzetti.justdrink.order.domain.shared.typed_ids.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrchestrateCheckoutSagaService implements OrchestrateCheckoutSaga {

  private final OrderPersistencePort orderPersistencePort;

  private final CustomerCommandPort customerCommandPort;
  //  private final KitchenCommandPort kitchenCommandPort;

  @Override
  public void startCheckoutSaga(StartCheckoutSagaCommand command) {
    log.warn("SAGA - CHECKOUT STARTED");
    Order theOrder = orderPersistencePort.findOrderByIdMandatory(command.orderId());
    CustomerId theCustomerId = theOrder.getCustomerId();
    OrderId theOrderId = theOrder.getOrderId();

    theOrder.setStatus(OrderStatus.PENDING_USER_VALIDATION);

    log.warn("SAGA - Imposto lo stato dell'ordine a: %s".formatted(theOrder.getStatus()));
    orderPersistencePort.saveOrder(theOrder);

    log.warn("SAGA - Chiedo al Customer-service di validare l'utente...");
    customerCommandPort.validateCustomer(theOrderId, theCustomerId);
  }

  @Override
  public void endCheckoutSaga(EndCheckoutSagaCommand command) {
    log.warn("SAGA - ...ricevo dal Customer-service la conferma della validazione");
    Order theOrder = orderPersistencePort.findOrderByIdMandatory(command.orderId());

    theOrder.setStatus(OrderStatus.COMPLETED);
    log.warn("SAGA - Imposto lo stato dell'ordine a: %s".formatted(theOrder.getStatus()));

    orderPersistencePort.saveOrder(theOrder);

    log.warn("SAGA - CHECKOUT COMPLETED");
  }
}
