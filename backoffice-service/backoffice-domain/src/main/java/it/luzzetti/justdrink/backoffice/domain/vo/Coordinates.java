package it.luzzetti.justdrink.backoffice.domain.vo;

import lombok.Builder;

/***
 * General coordinates Latitude & Longitude
 */
@Builder
public record Coordinates(Latitude latitude, Longitude longitude) {

  // Static Factory Method
  private static Coordinates of(Latitude latitude, Longitude longitude) {
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
}
