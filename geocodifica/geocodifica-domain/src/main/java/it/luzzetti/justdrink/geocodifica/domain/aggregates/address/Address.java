package it.luzzetti.justdrink.geocodifica.domain.aggregates.address;

import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;

@Getter
@Builder
public class Address {

  private String displayName;
  private Coordinate coordinate;

  public Address geociding(String displayNameDiInput) {

    return null;
  }

  public Address reverseGeocoding(Coordinate coordinateDiInput) {

    return null;
  }

  public static AddressBuilder builder() {
    return new Address.CustomBuilder();
  }

  private static class CustomBuilder extends AddressBuilder {

    public Address build() {

      if (!super.coordinate.isValid()) {
        // TODO fare le exception
        throw new IllegalArgumentException();
      }

      return super.build();
    }
  }
}
