package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto.CuisineResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface CuisineWebMapper {
  CuisineResource toResource(Cuisine theDomainCuisine);

  Cuisine toDomain(CuisineResource theResourceCuisine);

}
