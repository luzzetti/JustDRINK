package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import java.util.ArrayList;
import java.util.List;

public class GenerateRestaurants {

  public static List<Restaurant> generateRestaurants() {

    var result = new ArrayList<Restaurant>();
    result.add(
        Restaurant.builder()
            .name("Tana del cibo")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .name("Food 'n' Drinks")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .name("Poisoned Food")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
            .name("Christia'n'drinks")
            .address(
                Address.builder()
                    .coordinates(Coordinates.of(Latitude.of(12), Longitude.of(13)))
                    .displayName("Via dei matti numero 0")
                    .build())
            .build());
    result.add(
        Restaurant.builder()
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
