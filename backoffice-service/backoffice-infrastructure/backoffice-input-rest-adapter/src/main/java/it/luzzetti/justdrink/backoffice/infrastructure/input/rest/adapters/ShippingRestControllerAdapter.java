package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Polygon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ShippingRestControllerAdapter {

  @PostMapping("/area")
  public ResponseEntity<Void> createFeature(@RequestBody Polygon polygon) {
    // Handle the GeoJSON feature here
    // ...

    log.warn(() -> "Oh: %s".formatted(polygon));

    return ResponseEntity.ok().build();
  }
}
