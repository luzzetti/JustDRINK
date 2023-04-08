package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.WorktimeId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.WorktimeResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {OpeningWebMapper.class})
public interface WorktimeWebMapper {

  WorktimeResource toResource(Worktime domain);

  default UUID toUuid(WorktimeId worktimeId) {
    return worktimeId.id();
  }
}
