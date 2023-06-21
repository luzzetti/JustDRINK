package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.AccessLevel;
import lombok.Builder;

/***
 * General coordinates Latitude & Longitude
 */
@Builder(access = AccessLevel.PRIVATE)
public record Coordinates(Latitude latitude, Longitude longitude) {

  // Static Factory Method
  public static Coordinates of(Latitude latitude, Longitude longitude) {
    return Coordinates.builder().latitude(latitude).longitude(longitude).build();
  }

  // Same reasoning with typed ids, and wrapper class.
  // By typing them, we'll never switch them

  public record Latitude(double latitudeValue) {
    public static Latitude of(double latitudeValue) {
      return new Latitude(latitudeValue);
    }
  }

  public record Longitude(double longitudeValue) {
    public static Longitude of(double longitudeValue) {
      return new Longitude(longitudeValue);
    }
  }

  // Lombok's Builder Override

  private static CoordinatesBuilder builder() {
    return new Coordinates.CustomBuilder();
  }

  /*
   * Customized builder class, extends the Lombok generated builder class and overrides method
   * implementations.
   */
  private static class CustomBuilder extends CoordinatesBuilder {

    /* Adding validations as part of build() method. */
    public Coordinates build() {

      if (super.latitude == null || super.longitude == null) {
        throw new IllegalArgumentException("Both latitude and longitude must exist!");
      }

      return super.build();
    }
  }
}
