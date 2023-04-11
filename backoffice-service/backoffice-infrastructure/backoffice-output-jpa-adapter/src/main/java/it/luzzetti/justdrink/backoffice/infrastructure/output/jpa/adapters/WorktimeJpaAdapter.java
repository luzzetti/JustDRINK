package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.WorktimeJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.WorktimeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class WorktimeJpaAdapter implements FindWorktimePort, SaveWorktimePort {

  // Repositories
  private final WorktimeJpaRepository worktimeJpaRepository;
  private final RestaurantJpaRepository restaurantJpaRepository;
  // Mappers
  private final WorktimeJpaMapper worktimeJpaMapper;

  @Override
  public Worktime saveWorktime(Worktime aNewWorktime) {
    var theEntity = worktimeJpaMapper.toEntity(aNewWorktime);

    var theAssociatedRestaurant =
        restaurantJpaRepository.getReferenceById(aNewWorktime.getRestaurantId().id());
    theEntity.setRestaurant(theAssociatedRestaurant);

    var theSavedEntity = worktimeJpaRepository.save(theEntity);

    return worktimeJpaMapper.toDomain(theSavedEntity);
  }

  @Override
  public Worktime findWorktimeByRestaurantIdMandatory(RestaurantId restaurantId) {
    return worktimeJpaRepository
        .findWorktimeByRestaurantId(restaurantId.id())
        .map(worktimeJpaMapper::toDomain)
        .orElseThrow(() -> new IllegalArgumentException("Change this exception."));
  }
}
