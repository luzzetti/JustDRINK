package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleResource;
import org.mapstruct.Mapper;

@Mapper
public interface OverruleWebMapper {
  OverruleResource toResource(Overrule domain);
}
