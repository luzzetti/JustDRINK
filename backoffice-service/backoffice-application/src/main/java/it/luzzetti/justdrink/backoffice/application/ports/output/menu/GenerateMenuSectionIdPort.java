package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.MenuSectionId;

public interface GenerateMenuSectionIdPort {
  MenuSectionId nextMenuSectionIdentifier();
}
