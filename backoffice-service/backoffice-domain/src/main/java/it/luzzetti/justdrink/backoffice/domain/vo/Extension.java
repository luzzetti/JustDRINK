package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.Getter;

public class Extension {

  @Getter private final String value;

  public Extension(String value) {

    if (value == null) {
      throw new IllegalArgumentException();
    }

    if (!".JPG".equalsIgnoreCase(value)) {
      throw new IllegalArgumentException();
    }

    this.value = value;
  }

  public static Extension from(String aString) {
    return new Extension(aString);
  }
}
