package it.luzzetti.justdrink.geocodifica.application.ports.input.address;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface GeoparsePlaceUseCase {

  GeoparsedQuery<Place> geoparse(GeoparsePlaceCommand command);

  @Builder
  record GeoparsePlaceCommand(@NotNull String address, @NotNull Boolean exactMatch) {}
}
