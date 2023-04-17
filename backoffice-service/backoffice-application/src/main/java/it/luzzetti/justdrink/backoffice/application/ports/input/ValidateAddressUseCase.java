package it.luzzetti.justdrink.backoffice.application.ports.input;

import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.Builder;

public interface ValidateAddressUseCase {

  Address validateAddress(ValidateAddressCommand command);

  @Builder
  record ValidateAddressCommand(
      @NotNull @NotBlank String addressName, Optional<Coordinates> coordinates) {}
}
