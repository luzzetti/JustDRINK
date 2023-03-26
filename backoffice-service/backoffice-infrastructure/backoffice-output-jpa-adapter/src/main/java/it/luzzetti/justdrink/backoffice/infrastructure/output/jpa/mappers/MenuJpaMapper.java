package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RestaurantJpaMapper.class, MenuSectionJpaMapper.class})
public interface MenuJpaMapper {
  @Mapping(target = "restaurantId", source = "restaurant.id")
  Menu toDomain(MenuJpaEntity anEntity);

  @Mapping(target = "restaurant", ignore = true)
  MenuJpaEntity toEntity(Menu aMenu);

  default UUID map(MenuId menuId) {
    return menuId.id();
  }

  default MenuId map(UUID uuid) {
    return MenuId.from(uuid);
  }
}
