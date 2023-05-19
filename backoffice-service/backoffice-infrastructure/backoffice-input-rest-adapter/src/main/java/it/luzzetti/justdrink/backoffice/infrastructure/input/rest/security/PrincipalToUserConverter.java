package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security;

import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

public class PrincipalToUserConverter implements Converter<Jwt, CustomAuthenticationToken> {

  @Override
  public CustomAuthenticationToken convert(Jwt source) {

    String theUsername = source.getClaimAsString("preferred_username");

    Owner principal = Owner.builder().username(theUsername).build();

    return new CustomAuthenticationToken(source, principal);
  }
}
