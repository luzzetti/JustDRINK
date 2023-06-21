package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OwnerId;
import java.util.Objects;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

public class PrincipalToOwnerConverter implements Converter<Jwt, CustomAuthenticationToken> {

  @Override
  public CustomAuthenticationToken convert(Jwt source) {

    UUID theOwnerId = UUID.fromString(source.getClaimAsString("sid"));
    String theOwnerUsername = source.getClaimAsString("preferred_username");
    String theOwnerEmail = source.getClaimAsString("email");

    Objects.requireNonNull(theOwnerId);
    Objects.requireNonNull(theOwnerUsername);
    Objects.requireNonNull(theOwnerEmail);

    Owner principal =
        Owner.builder()
            .id(OwnerId.from(theOwnerId))
            .username(theOwnerUsername)
            .email(theOwnerEmail)
            .build();

    return new CustomAuthenticationToken(source, principal);
  }
}
