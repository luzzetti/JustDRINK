package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.AddressResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressWebMapper {

  @Mapping(target = "longitude", source = "coordinates.longitude.longitudeValue")
  @Mapping(target = "latitude", source = "coordinates.latitude.latitudeValue")
  AddressResource toResource(Address theAddress);
}
