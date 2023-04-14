package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.ClosingOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper
public interface OverruleWebMapper {

  @Mapping(target = "overruleType", ignore = true)
  OverruleResource toResource(Overrule overrule);

  @ObjectFactory
  default OverruleResource factory(Overrule domain) {

    if (Boolean.TRUE.equals(domain.getClosed())) {
      ClosingOverruleResource closingOverruleResource = new ClosingOverruleResource();
      closingOverruleResource.setOverruleType(OverruleType.CLOSING);
      return closingOverruleResource;

    } else {
      OpeningOverruleResource openingOverruleResource = new OpeningOverruleResource();
      openingOverruleResource.setOverruleType(OverruleType.OPENING);
      openingOverruleResource.setAlternativeOpenTime(domain.getAlternativeTimeslot().from());
      openingOverruleResource.setAlternativeCloseTime(domain.getAlternativeTimeslot().through());
      return openingOverruleResource;
    }
  }
}
