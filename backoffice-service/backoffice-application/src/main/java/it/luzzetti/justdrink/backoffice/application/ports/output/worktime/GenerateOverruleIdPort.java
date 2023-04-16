package it.luzzetti.justdrink.backoffice.application.ports.output.worktime;

import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;

public interface GenerateOverruleIdPort {
  OverruleId nextOverruleIdentifier();
}
