package it.luzzetti.justdrink.backoffice.application.services.delivery_area;

import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.ShowDeliveryAreaForRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area.FindDeliveryAreaPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowDeliveryAreaApplicationService implements ShowDeliveryAreaForRestaurantQuery {

  private final FindDeliveryAreaPort findDeliveryAreaPort;

  @Override
  public DeliveryArea showDeliveryArea(ShowDeliveryAreaCommand command) {
    log.debug(() -> String.format("showDeliveryArea(%s)", command));

    return findDeliveryAreaPort.findDeliveryAreaByRestaurantId(command.restaurantId());
  }
}
