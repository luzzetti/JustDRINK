package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.DatePeriod;
import java.time.DayOfWeek;
import lombok.Data;

@JsonTypeInfo(
    include = As.EXISTING_PROPERTY,
    property = "overruleType",
    use = Id.NAME,
    visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = OpeningOverruleResource.class, name = "OPENING"),
  @JsonSubTypes.Type(value = ClosingOverruleResource.class, name = "CLOSING")
})
@Data
public abstract class OverruleResource {
  private OverruleType overruleType;
  private DatePeriod validity;
  private DayOfWeek dayOfWeek;
}
