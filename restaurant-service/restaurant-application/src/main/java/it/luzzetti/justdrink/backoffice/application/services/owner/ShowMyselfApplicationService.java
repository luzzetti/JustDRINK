package it.luzzetti.justdrink.backoffice.application.services.owner;

import it.luzzetti.justdrink.backoffice.application.ports.input.owner.ShowMyselfQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowMyselfApplicationService implements ShowMyselfQuery {

  private final SecurityPort securityPort;

  @Override
  public Owner showMyself(ShowMyselfCommand command) {
    return securityPort.mySelf();
  }
}
