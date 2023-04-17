package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.Builder;

/***
 * Name is a textual representation of the address
 * eg: Via Cairoli 15, 01100, Italia
 * <p>
 * Coordinates are, obviously, coordinates
 */
@Builder
public record Address(String displayName, Coordinates coordinates) {

  // Lombok's Builder Override //

  public static AddressBuilder builder() {
    return new Address.CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends AddressBuilder {

    /* Adding validations as part of build() method. */
    public Address build() {

      if (super.displayName == null || super.displayName.isBlank()) {
        throw new IllegalArgumentException("AddressName is invalid. It cannot be null or empty");
      }

      if (super.coordinates == null) {
        throw new IllegalArgumentException(
            "Coordinates aren invalid. They cannot be null or empty");
      }

      return super.build();
    }
  }
}
