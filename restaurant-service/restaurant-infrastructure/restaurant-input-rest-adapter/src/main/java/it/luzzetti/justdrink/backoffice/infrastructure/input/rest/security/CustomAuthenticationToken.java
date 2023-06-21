package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security;

import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@EqualsAndHashCode(callSuper = false)
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

  private final Owner principal;
  private final Jwt credentials;

  public CustomAuthenticationToken(Jwt jwt, Owner principal) {
    super(getAuthorities(jwt));
    this.setAuthenticated(true);
    this.credentials = jwt;
    this.principal = principal;
  }

  private static List<SimpleGrantedAuthority> getAuthorities(Jwt source) {

    final Map<String, List<String>> realmAccess =
        Objects.requireNonNullElse(
            (Map<String, List<String>>) source.getClaims().get("realm_access"),
            Collections.emptyMap());

    return realmAccess.getOrDefault("roles", Collections.emptyList()).stream()
        .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
        .map(SimpleGrantedAuthority::new)
        .toList();
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }
}
