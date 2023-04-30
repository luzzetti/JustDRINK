package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security.CustomAuthenticationToken;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * Da Fare:
 * Raffinare il modello di sicurezza ABAC, se necessario, rendendo la manipolazione
 * delle policy più dinamica.
 *
 * Possono essere caricate dal DB, o comunque esternamente, ed essere modificate a runtime.
 * Al momento, tuttavia, il progetto non richiede questo livello di sofisticatezza.
 * Inoltre, mi sono anche abbastanza rotto il cazzo di lavorare su questa parte, quindi... :)
 *
 * https://dzone.com/articles/simple-attribute-based-access-control-with-spring
 *
 * https://www.baeldung.com/java-access-control-models
 */

@Component
@Log4j2
@RequiredArgsConstructor
public class SecuritySpringAdapter implements SecurityPort {

  @Override
  public Owner mySelf() {
    return (Owner) getAuthentication().getPrincipal();
  }

  @Override
  public boolean iHaveTheRole(String role) {
    return getAuthentication().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(authority -> authority.replaceFirst("ROLE_", ""))
        .anyMatch(authority -> authority.equals(role.toUpperCase()));
  }

  @Override
  public List<String> myRoles() {
    return getAuthentication().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(authority -> authority.replaceFirst("ROLE_", ""))
        .map(String::toUpperCase)
        .toList();
  }

  private Authentication getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(Authentication::isAuthenticated)
        .filter(CustomAuthenticationToken.class::isInstance)
        .orElseThrow(
            () -> new OwnerNotAuthenticatedException(SecurityErrors.OWNER_NOT_AUTHENTICATED));
  }
}
