package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Latitude;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates.Longitude;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.AddressJpaEmbeddable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface AddressJpaMapper {

  @Mapping(target = "coordinates", source = ".", qualifiedByName = "toCoordinates")
  Address toDomain(AddressJpaEmbeddable embeddable);

  @Mapping(target = "longitude", source = "coordinates", qualifiedByName = "toLongitude")
  @Mapping(target = "latitude", source = "coordinates", qualifiedByName = "toLatitude")
  AddressJpaEmbeddable toEntity(Address domain);

  @Named("toCoordinates")
  default Coordinates toCoordinates(AddressJpaEmbeddable embeddable) {
    return Coordinates.of(
        Latitude.of(embeddable.getLatitude()), Longitude.of(embeddable.getLongitude()));
  }

  @Named("toLongitude")
  default double toLongitude(Coordinates coordinates) {
    return coordinates.longitude().longitudeValue();
  }

  @Named("toLatitude")
  default double toLatitude(Coordinates coordinates) {
    return coordinates.latitude().latitudeValue();
  }
}
