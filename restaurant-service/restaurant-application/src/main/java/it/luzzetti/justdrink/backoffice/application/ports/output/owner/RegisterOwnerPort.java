package it.luzzetti.justdrink.backoffice.application.ports.output.owner;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;

public interface RegisterOwnerPort {

  Owner registerOwner(String email);
}
