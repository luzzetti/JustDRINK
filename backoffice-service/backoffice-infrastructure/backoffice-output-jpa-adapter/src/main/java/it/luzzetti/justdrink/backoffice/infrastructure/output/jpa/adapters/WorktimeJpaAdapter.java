package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.DeleteWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateOpeningIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateOverruleIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateWorktimeIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OpeningId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.WorktimeId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.WorktimeJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.WorktimeJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class WorktimeJpaAdapter
    implements FindWorktimePort,
        SaveWorktimePort,
        DeleteWorktimePort,
        GenerateWorktimeIdPort,
        GenerateOverruleIdPort,
        GenerateOpeningIdPort {

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
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "There is no worktime associated with a restaurantId of %s"
                        .formatted(restaurantId)));
  }

  @Override
  public void deleteWorktimeByRestaurantId(RestaurantId restaurantId) {
    worktimeJpaRepository
        .findWorktimeByRestaurantId(restaurantId.id())
        .ifPresent(worktimeJpaRepository::delete);
  }

  @Override
  public OpeningId nextOpeningIdentifier() {
    return OpeningId.from(UUID.randomUUID());
  }

  @Override
  public OverruleId nextOverruleIdentifier() {
    return OverruleId.from(UUID.randomUUID());
  }

  @Override
  public WorktimeId nextWorktimeIdentifier() {
    return WorktimeId.from(UUID.randomUUID());
  }
}
