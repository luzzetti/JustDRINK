package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantHistoryQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.ShowRestaurantHistoryQuery.ShowRestaurantHistoryCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/restaurants")
@Log4j2
@RequiredArgsConstructor
public class RestaurantHistoryRestControllerAdapter {

  // Queries
  private final ShowRestaurantHistoryQuery showRestaurantHistoryQuery;

  @GetMapping("{restaurantId}:showHistory")
  public ResponseEntity<List<StoRestaurant>> showRestaurantHistory(
      @PathVariable UUID restaurantId) {

    // Fetching Data
    var command =
        ShowRestaurantHistoryCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .build();

    // Executing the UseCase/Query + Crafting the response
    List<StoRestaurant> stoRestaurantList = new ArrayList<>();

    for (var anEntry : showRestaurantHistoryQuery.showRestaurantHistory(command).entrySet()) {
      stoRestaurantList.add(new StoRestaurant(anEntry.getKey(), anEntry.getValue()));
    }

    stoRestaurantList.sort(Comparator.comparing(StoRestaurant::getUpdatedAt).reversed());

    return ResponseEntity.ok(stoRestaurantList);
  }

  // Il nome è solo in onore della storicizzazione 118
  @Value
  public static class StoRestaurant {
    String updatedAt;
    Restaurant restaurant;

    public StoRestaurant(Instant updatedAt, Restaurant restaurant) {
      var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      this.updatedAt = LocalDateTime.ofInstant(updatedAt, ZoneOffset.UTC).format(formatter);
      this.restaurant = restaurant;
    }
  }
}
