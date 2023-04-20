package it.luzzetti.justdrink.backoffice.domain;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileImage {

  private RestaurantId restaurantId;
  private String name;
  private InputStream inputStream;


}
