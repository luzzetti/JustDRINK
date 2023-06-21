package it.luzzetti.justdrink.backoffice.application.ports.input.owner;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface RegisterOwnerUseCase {

  Owner registerOwner(RegisterOwnerCommand command);

  @Builder
  record RegisterOwnerCommand(@NotNull @NotBlank String email) {}
}
