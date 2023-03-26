package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.MenuSection;
import it.luzzetti.justdrink.backoffice.domain.shared.MenuSectionId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.MenuSectionResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface MenuSectionWebMapper {
  MenuSectionResource toResource(MenuSection theCreatedMenuSection);

  default UUID toUuid(MenuSectionId menuSectionId) {
    return menuSectionId.id();
  }
}
