package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

public interface FindRestaurantsByCliendAdressUseCase {


  List<Restaurant> findRestaurantbyClientAdress(FindRestaurantsByClientAdressCommand command);

  @Builder
  record FindRestaurantsByClientAdressCommand(
      @NotNull @NotEmpty String addressName,
      Optional<Coordinates> coordinates) {}

}
