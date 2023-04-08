package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.WorktimeId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.WorktimeJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RestaurantJpaMapper.class, OpeningJpaMapper.class})
public interface WorktimeJpaMapper {

  default UUID map(WorktimeId worktimeId) {
    return worktimeId.id();
  }

  default WorktimeId map(UUID uuid) {
    return WorktimeId.from(uuid);
  }

  @Mapping(target = "restaurant", ignore = true)
  WorktimeJpaEntity toEntity(Worktime aNewWorktime);

  @Mapping(target = "restaurantId", source = "restaurant.id")
  Worktime toDomain(WorktimeJpaEntity theSavedEntity);
}
