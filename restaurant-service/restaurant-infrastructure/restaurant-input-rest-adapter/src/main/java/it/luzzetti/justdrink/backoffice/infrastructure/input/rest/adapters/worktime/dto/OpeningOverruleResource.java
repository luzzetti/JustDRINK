package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpeningOverruleResource extends OverruleResource {
  private Timeslot alternativeShift;
}
