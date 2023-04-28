package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface UploadLogoUsecase {

  void uploadLogoRestaurant(UploadLogoRetaurantCommand uoloadLoroRetaurantCommand);

  @Builder
  public record UploadLogoRetaurantCommand(@NotNull RestaurantId restaurantId, String nomeFile,String path){}

}
