package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        auth ->
            auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                .permitAll()
                .anyRequest()
                .permitAll());

    // Dico al WebServer che non voglio cookie
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // SENZA DI QUESTO, LA corsConfigurationSource NON VIENE ELABORATA!
    http.cors();

    http.csrf().disable();

    http.oauth2ResourceServer()
        .jwt(customizer -> customizer.jwtAuthenticationConverter(new PrincipalToUserConverter()));

    return http.build();
  }

  /***
   * Attenzione!
   * Questo BEAN viene preso solamente se nelle configurazioni sopra, è presente:
   * http.cors()
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("http://localhost:4200"));

    configuration.setAllowedMethods(
        List.of(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /* Extra:
   * Mapping avanzato per ruoli https://www.baeldung.com/spring-security-map-authorities-jwt
   */

}
