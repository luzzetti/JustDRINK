package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security;

import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import java.util.List;
import java.util.Map;
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
    final Map<String, Object> realmAccess =
        (Map<String, Object>) source.getClaims().get("realm_access");

    return ((List<String>) realmAccess.get("roles"))
        .stream()
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
