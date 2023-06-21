package it.luzzetti.justdrink.geocodifica.domain.aggregates.address;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.ElementNotValidException;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;

@Builder
public class Place {

  @Getter private String address;
  private Coordinate geo;

  public double getLatitude() {
    return geo.getX();
  }

  public double getLongitude() {
    return geo.getY();
  }

  public static PlaceBuilder builder() {
    return new ValidatorBuilder();
  }

  private static class ValidatorBuilder extends PlaceBuilder {

    public Place build() {

      if (super.address == null || super.address.isBlank()) {
        throw new ElementNotValidException(PlaceErrors.ADDRESS_REQUIRED);
      }

      if (!super.geo.isValid()) {
        throw new ElementNotProcessableException(PlaceErrors.IMPOSSIBLE_TO_GEOCODE);
      }

      return super.build();
    }
  }
}
