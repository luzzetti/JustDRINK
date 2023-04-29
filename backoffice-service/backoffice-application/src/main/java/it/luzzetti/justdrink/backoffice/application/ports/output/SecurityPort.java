package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ApplicationException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ErrorCode;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;
import java.util.List;

/*
 * TODO: Raffinare
 * https://lostechies.com/derickbailey/2011/05/24/dont-do-role-based-authorization-checks-do-activity-based-checks/
 */
public interface SecurityPort {

  // ritrovo il principal. (Il buon vecchio 'Utente sessione')
  Owner mySelf();

  // Altri metodi utili ad estrarre principal e ruoli
  boolean iHaveTheRole(String role);

  // ritrovo i ruoli del principal
  List<String> myRoles();

  void assertThatUserHasPermissionToCreateRestaurant();

  void assertThatUserHasPermissionToDeleteRestaurant(Restaurant theRestaurant);

  // Security related exceptions

  class OwnerNotAuthenticatedException extends ApplicationException {
    public OwnerNotAuthenticatedException(ErrorCode errorCode) {
      super(errorCode);
    }
  }

  class OwnerNotAuthorizedException extends ApplicationException {
    public OwnerNotAuthorizedException(ErrorCode errorCode) {
      super(errorCode);
    }
  }

  enum SecurityErrors implements ErrorCode {
    OWNER_NOT_AUTHENTICATED("security.owner_not_authenticated"),
    UNAUTHORIZED("security.owner_not_authorized"),
    UNAUTHORIZED_RESTAURANT_CREATION("security.owner_not_authorized.restaurant.creation"),
    UNAUTHORIZED_RESTAURANT_DELETION("security.owner_not_authorized.restaurant.deletion");

    private final String code;

    SecurityErrors(String code) {
      this.code = code;
    }

    @Override
    public String getCode() {
      return this.code;
    }
  }
}
