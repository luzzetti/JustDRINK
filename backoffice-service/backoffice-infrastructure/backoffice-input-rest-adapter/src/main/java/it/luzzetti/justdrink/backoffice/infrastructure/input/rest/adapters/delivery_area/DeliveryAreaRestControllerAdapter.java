package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.delivery_area;

import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.CreateDeliveryAreaUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.CreateDeliveryAreaUseCase.CreateDeliveryAreaCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants/{restaurantId}/shipping")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class DeliveryAreaRestControllerAdapter {

  private final CreateDeliveryAreaUseCase createDeliveryAreaUseCase;

  @PostMapping("/area")
  public ResponseEntity<Void> createDeliveryArea(
      @PathVariable UUID restaurantId, @RequestBody Polygon polygon) {

    var command =
        CreateDeliveryAreaCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .polygon(polygon)
            .build();

    DeliveryArea theCreatedDeliveryArea = createDeliveryAreaUseCase.createDeliveryArea(command);

    log.warn("DeliveryArea: %s".formatted(theCreatedDeliveryArea));

    return ResponseEntity.ok().build();
  }
}
