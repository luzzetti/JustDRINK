package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.dto.PlaceResource;
import it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.place.PlaceRestControllerAdapter.GeoparseResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PlacesWebMapper {

  GeoparseResponse toResponse(GeoparsedQuery<Place> result);

  PlaceResource toResource(Place place);
}
