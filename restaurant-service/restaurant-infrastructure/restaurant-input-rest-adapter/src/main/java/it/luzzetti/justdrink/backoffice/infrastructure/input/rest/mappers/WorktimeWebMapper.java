package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.WorktimeResource;
import org.mapstruct.Mapper;

@Mapper(uses = {OpeningWebMapper.class, OverruleWebMapper.class})
public interface WorktimeWebMapper {

  WorktimeResource toResource(Worktime domain);
}
