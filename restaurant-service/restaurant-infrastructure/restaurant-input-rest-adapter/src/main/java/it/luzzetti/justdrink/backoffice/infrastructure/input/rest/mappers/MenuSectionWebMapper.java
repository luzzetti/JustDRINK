package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto.MenuSectionResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface MenuSectionWebMapper {
  MenuSectionResource toResource(MenuSection theCreatedMenuSection);

  default UUID toUuid(MenuSectionId menuSectionId) {
    return menuSectionId.id();
  }
}
