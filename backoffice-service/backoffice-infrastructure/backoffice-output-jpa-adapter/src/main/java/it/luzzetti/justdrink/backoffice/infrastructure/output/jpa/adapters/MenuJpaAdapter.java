package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.menu.DeleteMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.GenerateMenuIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.GenerateMenuSectionIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.GenerateProductIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.MenuJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.MenuJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class MenuJpaAdapter
    implements FindMenuPort,
        SaveMenuPort,
        DeleteMenuPort,
        GenerateMenuIdPort,
        GenerateMenuSectionIdPort,
        GenerateProductIdPort {
  private final MenuJpaRepository menuJpaRepository;
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final MenuJpaMapper menuJpaMapper;

  @Override
  public Menu findMenuByRestaurantIdMandatory(RestaurantId restaurantId) {
    return menuJpaRepository
        .findMenuByRestaurantId(restaurantId.id())
        .map(menuJpaMapper::toDomain)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "There is no Menu associated with a restaurantId of %s"
                        .formatted(restaurantId)));
  }

  @Override
  public Menu saveMenu(Menu aMenuToUpdate) {
    // Fetching data
    MenuJpaEntity menuJpaEntity = menuJpaMapper.toEntity(aMenuToUpdate);

    // Mantenere l'associazione a mano anziché nel mapper?
    var theAssociatedRestaurant =
        restaurantJpaRepository.getReferenceById(aMenuToUpdate.getRestaurantId().id());
    menuJpaEntity.setRestaurant(theAssociatedRestaurant);

    // Use Case
    MenuJpaEntity theReplacedMenu = menuJpaRepository.save(menuJpaEntity);

    // Returning a Response
    return menuJpaMapper.toDomain(theReplacedMenu);
  }

  @Override
  public void deleteMenuByRestaurantId(RestaurantId restaurantId) {
    menuJpaRepository
        .findMenuByRestaurantId(restaurantId.id())
        .ifPresent(menuJpaRepository::delete);
  }

  @Override
  public MenuId nextMenuIdentifier() {
    return MenuId.from(UUID.randomUUID());
  }

  @Override
  public MenuSectionId nextMenuSectionIdentifier() {
    return MenuSectionId.from(UUID.randomUUID());
  }

  @Override
  public ProductId nextProductIdentifier() {
    return ProductId.from(UUID.randomUUID());
  }
}
