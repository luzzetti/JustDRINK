package it.luzzetti.justdrink.backoffice.application.ports.input.restaurant;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.io.InputStream;
import lombok.Builder;
import lombok.NonNull;

public interface UploadLogoRestaurantUseCase {

  String updateLogo(UploadFileImageCommand uploadFileImageCommand);

  @Builder
  record UploadFileImageCommand(@NonNull RestaurantId restaurantId, @NonNull InputStream inputStream, @NonNull String name) {

  }

}
