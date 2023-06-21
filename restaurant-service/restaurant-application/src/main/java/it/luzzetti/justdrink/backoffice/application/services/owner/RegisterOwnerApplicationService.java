package it.luzzetti.justdrink.backoffice.application.services.owner;

import it.luzzetti.justdrink.backoffice.application.ports.input.owner.RegisterOwnerUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.owner.RegisterOwnerPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class RegisterOwnerApplicationService implements RegisterOwnerUseCase {

  private final RegisterOwnerPort registerOwnerPort;

  @Override
  @Transactional
  public Owner registerOwner(@Valid RegisterOwnerCommand command) {
    log.debug(() -> "registerOwner(%s)".formatted(command));

    Owner theRegisteredOwner = registerOwnerPort.registerOwner(command.email());

    log.debug(() -> "a new owner has been registered: %s".formatted(theRegisteredOwner));
    return theRegisteredOwner;
  }
}
