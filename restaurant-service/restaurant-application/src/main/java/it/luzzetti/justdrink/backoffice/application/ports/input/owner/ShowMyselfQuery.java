package it.luzzetti.justdrink.backoffice.application.ports.input.owner;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import lombok.Builder;

public interface ShowMyselfQuery {

  Owner showMyself(ShowMyselfCommand command);

  @Builder
  record ShowMyselfCommand() {}
}
