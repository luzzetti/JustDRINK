package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto.AddressResource;
import org.mapstruct.Mapper;

@Mapper
public interface AddressWebMapper {

  AddressResource toResource(Address address);
}
