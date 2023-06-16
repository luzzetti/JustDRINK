package it.luzzetti.justdrink.geocodifica.application.ports.input.address;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

public interface GeocodeAddressUseCase {

  public List<Address> geocoding(GeocodingAddressCommand command);

  @Builder
  record GeocodingAddressCommand(@NotNull String displayName) {}
}
