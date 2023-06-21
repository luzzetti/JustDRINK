package it.luzzetti.justdrink.geocodifica.infrastructure.output.rest_client.mappers;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.infrastructure.output.rest_client.adapters.PlacesRestClientAdapter.ExternalGeoparseResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PlaceRestClientMapper {

  @Mapping(target = "geo", source = ".")
  @Mapping(target = "address", source = "display_name")
  Place toDomain(ExternalGeoparseResponse aPlace);

  default Coordinate map(ExternalGeoparseResponse aPlace) {
    return new CoordinateXY(aPlace.lat(), aPlace.lon());
  }
}
