package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ApplicationException;
import it.luzzetti.justdrink.backoffice.domain.shared.exceptions.ErrorCode;
import it.luzzetti.justdrink.backoffice.domain.vo.Owner;

public interface SecurityPort {

  String ROLE_ADMIN = "ADMIN";

  Owner mySelf();

  boolean iHaveTheRole(String role);

  // Policies

  default void assertThatUserHasPermissionToCreateRestaurant() {
    if (iHaveTheRole(ROLE_ADMIN)) {
      return;
    }

    throw new OwnerNotAuthorizedException(SecurityErrors.UNAUTHORIZED_RESTAURANT_CREATION);
  }

  default void assertThatUserHasPermissionToDeleteRestaurant(Restaurant theRestaurant) {
    if (iHaveTheRole(ROLE_ADMIN)) {
      return;
    }

    if (theRestaurant.isOwnedBy(mySelf())) {
      return;
    }

    throw new OwnerNotAuthorizedException(SecurityErrors.UNAUTHORIZED_RESTAURANT_DELETION);
  }

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
