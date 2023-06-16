package it.luzzetti.justdrink.geocodifica.domain.aggregates.address;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;

@Getter
@Builder
public class Address {

  private String displayName;
  private Coordinate coordinate;

  public static AddressBuilder builder() {
    return new Address.CustomBuilder();
  }

  private static class CustomBuilder extends AddressBuilder {

    public Address build() {

      if (!super.coordinate.isValid()) {
        throw new ElementNotProcessableException(AddressErrors.IMPOSSIBLE_TO_GEOCODE);
      }

      return super.build();
    }
  }
}
