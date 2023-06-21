package it.luzzetti.justdrink.backoffice.domain.aggregates.owner;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OwnerId;
import java.io.Serializable;
import java.security.Principal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Owner implements Principal, Serializable {

  private final OwnerId id;
  private final String username;
  private final String email;

  @Override
  public String getName() {
    return email;
  }
}
