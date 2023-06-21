package it.luzzetti.justdrink.backoffice.infrastructure.output.keycloak_admin_client.adapter;

import it.luzzetti.commons.exceptions.ElementNotProcessableException;
import it.luzzetti.commons.exceptions.ErrorCode;
import it.luzzetti.justdrink.backoffice.application.ports.output.owner.RegisterOwnerPort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OwnerId;
import java.util.Collections;
import java.util.UUID;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

/***
 * Vecchia implementazione di esempio:
 * <a href="https://gist.github.com/thomasdarimont/c4e739c5a319cf78a4cff3b87173a84b">link</a>
 * Init del proxy
 * <a href="https://www.baeldung.com/java-keycloak-search-users">init keycloak proxy</a>
 */

@Component
@RequiredArgsConstructor
@Log4j2
public class OwnerKeycloakAdapter implements RegisterOwnerPort {

  private final Keycloak keycloak;

  @Override
  public Owner registerOwner(String email) {

    UserRepresentation theNewUser = new UserRepresentation();

    theNewUser.setUsername(email);
    theNewUser.setEmail(email);
    theNewUser.setEmailVerified(true);
    theNewUser.setEnabled(true);

    // Credentials Creation
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue("test");
    // .Credentials Creation
    theNewUser.setCredentials(Collections.singletonList(passwordCredentials));

    UserResource theCreatedUser = createUserOnKeycloak(theNewUser);

    // Adding roles to user
    RoleRepresentation ownerRole = getRolesResource().get("restaurant-owner").toRepresentation();
    theCreatedUser.roles().realmLevel().add(Collections.singletonList(ownerRole));

    UUID theId = UUID.fromString(theCreatedUser.toRepresentation().getId());
    String theEmail = theCreatedUser.toRepresentation().getEmail();

    return Owner.builder().id(OwnerId.from(theId)).email(theEmail).build();
  }

  public UsersResource getUsersResource() {
    return keycloak.realm("justdrink").users();
  }

  public RolesResource getRolesResource() {
    return keycloak.realm("justdrink").roles();
  }

  private UserResource createUserOnKeycloak(UserRepresentation user) {

    try (Response response = getUsersResource().create(user)) {

      if (response.getStatus() == Status.CONFLICT.getStatusCode()) {
        throw new ElementNotProcessableException(RegistrationErrors.CONFLICT);
      }

      if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
        throw new ElementNotProcessableException(RegistrationErrors.GENERAL_ERROR);
      }

      String theCreatedUserId = CreatedResponseUtil.getCreatedId(response);
      return getUsersResource().get(theCreatedUserId);
    }
  }
}

enum RegistrationErrors implements ErrorCode {
  GENERAL_ERROR("keycloak.unknown.error"),
  CONFLICT("keycloak.conflict.user.already.existing");

  private final String code;

  RegistrationErrors(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return this.code;
  }
}
