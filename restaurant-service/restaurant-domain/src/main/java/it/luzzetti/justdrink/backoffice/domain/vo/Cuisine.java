package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Cuisine {
  String name;

  private Cuisine(String name) {
    this.name = name;
  }

  public static Cuisine of(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("CuisineName is invalid. It cannot be null or empty");
    }

    return new Cuisine(name);
  }
}
