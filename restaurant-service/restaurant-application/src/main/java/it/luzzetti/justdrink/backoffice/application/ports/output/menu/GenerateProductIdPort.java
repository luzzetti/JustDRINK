package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.ProductId;

public interface GenerateProductIdPort {
  ProductId nextProductIdentifier();
}
