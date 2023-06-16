package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa;

import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/***
 * Configuring an Auditor to fill the 'CreatedBy' and 'LastModifiedBy' fields in the AuditableEntity
 */

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public @NotNull Optional<String> getCurrentAuditor() {
    var principal =
        (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Optional.of(principal.getName());
  }
}
