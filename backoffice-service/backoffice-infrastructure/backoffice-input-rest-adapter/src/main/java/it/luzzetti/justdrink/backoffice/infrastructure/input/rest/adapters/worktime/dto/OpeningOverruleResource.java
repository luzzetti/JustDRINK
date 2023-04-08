package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import java.time.LocalTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpeningOverruleResource extends OverruleResource {
  private LocalTime alternativeOpenTime;
  private LocalTime alternativeCloseTime;
}
