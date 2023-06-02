package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.delivery_area;

import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.SetDeliveryAreaForRestaurantUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.SetDeliveryAreaForRestaurantUseCase.SetDeliveryAreaCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.ShowDeliveryAreaForRestaurantQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.delivery_area.ShowDeliveryAreaForRestaurantQuery.ShowDeliveryAreaCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.delivery_area.DeliveryArea;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants/{restaurantId}/delivery/area")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class DeliveryAreaRestControllerAdapter {

  private final SetDeliveryAreaForRestaurantUseCase setDeliveryAreaUseCase;
  private final ShowDeliveryAreaForRestaurantQuery showDeliveryAreaQuery;

  @GetMapping
  public ResponseEntity<DeliveryArea> showDeliveryArea(@PathVariable UUID restaurantId) {

    var command =
        ShowDeliveryAreaCommand.builder().restaurantId(RestaurantId.from(restaurantId)).build();

    DeliveryArea theFoundArea = showDeliveryAreaQuery.showDeliveryArea(command);
    return ResponseEntity.ok(theFoundArea);
  }

  @PutMapping
  public ResponseEntity<DeliveryArea> setDeliveryArea(
      @PathVariable UUID restaurantId, @RequestBody Polygon polygon) {

    var command =
        SetDeliveryAreaCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .polygon(polygon)
            .build();

    DeliveryArea theUpdatedArea = setDeliveryAreaUseCase.setDeliveryArea(command);
    return ResponseEntity.ok(theUpdatedArea);
  }
}
