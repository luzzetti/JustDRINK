package it.luzzetti.justdrink.backoffice.infrastructure.output.keycloak_admin_client;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfigurations {

  // TODO: non HARDCODE questi valori!!!
  // TODO: Creare un utenza di sistema!

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl("http://justdrink-keycloak:8080")
        .realm("master")
        .clientId("admin-cli")
        .grantType(OAuth2Constants.PASSWORD)
        // .username("justdrink.admin@gmail.com")
        // .password("justdrink.admin")
        .username("admin")
        .password("admin")
        .build();
  }
}
