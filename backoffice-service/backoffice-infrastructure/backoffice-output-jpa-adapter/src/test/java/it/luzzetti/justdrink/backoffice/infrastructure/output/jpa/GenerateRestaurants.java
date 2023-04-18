package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenerateRestaurants {

  public static List<Restaurant> generateRestaurants() {

    var result = new ArrayList<Restaurant>();
    result.add(
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("Tana del cibo")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("Food 'n' Drinks")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("Poisoned Food")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("Christia'n'drinks")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .id(RestaurantId.from(UUID.randomUUID()))
            .name("JustFood")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    return result;
  }
}
