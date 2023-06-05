package it.luzzetti.justdrink.backoffice.domain.vo;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.RestaurantErrors;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Extension {

  private static final Set<String> extensionsSupported =
      new HashSet<>(Arrays.asList(".JPG", ".PNG"));

  @Getter private final String value;

  public Extension(String value) {

    if (value == null) {
      throw new IllegalArgumentException();
    }

    if (extensionsSupported.stream().noneMatch(value::equalsIgnoreCase)) {
      throw new ElementNotProcessableException(RestaurantErrors.LOGO_UPLOAD_IMPOSSIBLE);
    }

    this.value = value;
  }

  public static Extension from(String aString) {
    return new Extension(aString);
  }
}
