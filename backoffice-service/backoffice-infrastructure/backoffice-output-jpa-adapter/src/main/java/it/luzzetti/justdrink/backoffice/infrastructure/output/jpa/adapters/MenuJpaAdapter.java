package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.menu.DeleteMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.FindMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.SaveMenuPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.menu.UpdateMenuPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.RestaurantJpaEntity;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.MenuJpaMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.MenuJpaRepository;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class MenuJpaAdapter implements FindMenuPort, SaveMenuPort, UpdateMenuPort, DeleteMenuPort {
  private final MenuJpaRepository menuJpaRepository;
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final MenuJpaMapper menuJpaMapper;

  @Override
  public Menu createMenu(Menu aNewMenu) {

    MenuJpaEntity aMenuEntity = menuJpaMapper.toEntity(aNewMenu);
    RestaurantJpaEntity theAssociatedRestaurant =
        restaurantJpaRepository.getReferenceById(aNewMenu.getRestaurantId().id());
    aMenuEntity.setRestaurant(theAssociatedRestaurant);

    MenuJpaEntity theCreatedMenu = menuJpaRepository.save(aMenuEntity);

    return menuJpaMapper.toDomain(theCreatedMenu);
  }

  @Override
  public Menu findMenuByRestaurantIdMandatory(RestaurantId restaurantId) {
    return menuJpaRepository
        .findMenuByRestaurantId(restaurantId.id())
        .map(menuJpaMapper::toDomain)
        .orElseThrow(() -> new IllegalArgumentException("Change this exception"));
  }

  @Override
  public Menu updateMenu(Menu aMenuToUpdate) {
    // Fetching data
    // Mantenere l'associazione a mano anziché nel mapper?
    MenuJpaEntity menuJpaEntity = menuJpaMapper.toEntity(aMenuToUpdate);

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
}
