package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.Builder;

public interface LoadLogoRestaurantUseCase {

  byte[] loadLogo(LoadLogoCommand loadLogoCommand) throws IOException;

  @Builder
  public record LoadLogoCommand(@NotNull RestaurantId restaurantId){}

}
