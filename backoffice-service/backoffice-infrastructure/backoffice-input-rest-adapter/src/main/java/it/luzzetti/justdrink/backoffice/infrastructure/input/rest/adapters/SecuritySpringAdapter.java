package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.output.SecurityPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * Beh...questa class NON DOVREBBE stare quì, ma non so ancora bene dove ficcarla.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class SecuritySpringAdapter implements SecurityPort {

  /*
   * Da notare come, al momento, questa implementazione dei ruoli segua un modello RBAC.
   * Io però voglio un ABAC.
   *
   * Al momento, tuttavia, mi sono rotto il cazzo di lavorare su questa parte, quindi... :)
   *
   */

  private static final String ROLE_ADMIN = "ADMIN";
  private static final String ROLE_OWNER = "OWNER";

  // Policies
  @Override
  public void assertThatUserHasPermissionToCreateRestaurant() {

    if (iHaveTheRole(ROLE_ADMIN)) {
      return;
    }

    throw new OwnerNotAuthorizedException(SecurityErrors.UNAUTHORIZED_RESTAURANT_CREATION);
  }

  @Override
  public void assertThatUserHasPermissionToDeleteRestaurant(Restaurant theRestaurant) {

    if (iHaveTheRole(ROLE_ADMIN)) {
      return;
    }

    if (theRestaurant.isOwnedBy(mySelf())) {
      return;
    }

    throw new OwnerNotAuthorizedException(SecurityErrors.UNAUTHORIZED_RESTAURANT_DELETION);
  }

  // Altro
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

  @Override
  public Owner mySelf() {
    return (Owner) getAuthentication().getPrincipal();
  }

  private Authentication getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(
            authentication ->
                authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken))
        .orElseThrow(
            () -> new OwnerNotAuthenticatedException(SecurityErrors.OWNER_NOT_AUTHENTICATED));
  }
}
