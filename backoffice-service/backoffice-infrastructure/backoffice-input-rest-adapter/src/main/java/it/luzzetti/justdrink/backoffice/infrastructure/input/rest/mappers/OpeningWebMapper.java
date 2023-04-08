package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningResource;
import org.mapstruct.Mapper;

@Mapper
public interface OpeningWebMapper {
  OpeningResource toResource(Opening domain);
}
