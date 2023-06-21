package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuSectionJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductJpaMapper.class})
public interface MenuSectionJpaMapper {
  MenuSection toDomain(MenuSectionJpaEntity anEntity);
  MenuSectionJpaEntity toEntity(MenuSection aMenu);

  default UUID map(MenuSectionId aTypedId) {
    return aTypedId.id();
  }

  default MenuSectionId map(UUID uuid) {
    return MenuSectionId.from(uuid);
  }
}
