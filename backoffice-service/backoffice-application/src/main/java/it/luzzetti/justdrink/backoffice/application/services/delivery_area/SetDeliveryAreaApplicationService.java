package it.luzzetti.justdrink.backoffice.application.services.delivery_area;

import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.SetDeliveryAreaForRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.delivery_area.SaveDeliveryAreaPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SetDeliveryAreaApplicationService implements SetDeliveryAreaForRestaurantUseCase {

  private final SaveDeliveryAreaPort saveDeliveryAreaPort;

  @Override
  @Transactional
  public DeliveryArea setDeliveryArea(SetDeliveryAreaCommand command) {
    log.debug(() -> String.format("createDeliveryArea(%s)", command));

    DeliveryArea aNewDeliveryArea =
        DeliveryArea.builder()
            .restaurantId(command.restaurantId())
            .polygon(command.polygon())
            .build();

    return saveDeliveryAreaPort.saveDeliveryArea(aNewDeliveryArea);
  }
}
