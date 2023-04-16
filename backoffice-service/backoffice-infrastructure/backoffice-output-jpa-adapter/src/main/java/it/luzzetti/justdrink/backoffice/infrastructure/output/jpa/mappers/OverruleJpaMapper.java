package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.DatePeriod;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OverruleJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface OverruleJpaMapper {

  @Mapping(target = "validThrough", source = "validity.through")
  @Mapping(target = "validFrom", source = "validity.from")
  @Mapping(target = "alternativeOpenTime", source = "alternativeShift.from")
  @Mapping(target = "alternativeCloseTime", source = "alternativeShift.through")
  OverruleJpaEntity toEntity(Overrule domain);

  @Mapping(target = "validity", source = ".", qualifiedByName = "toValidityFromEntity")
  @Mapping(target = "alternativeShift", source = ".", qualifiedByName = "toShiftFromEntity")
  Overrule toDomain(OverruleJpaEntity entity);

  @Named("toShiftFromEntity")
  default Timeslot toShift(OverruleJpaEntity entity) {
    if (Boolean.TRUE.equals(entity.getClosed())) {
      return null;
    }
    return Timeslot.builder()
        .from(entity.getAlternativeOpenTime())
        .through(entity.getAlternativeCloseTime())
        .build();
  }

  @Named("toValidityFromEntity")
  default DatePeriod toValidity(OverruleJpaEntity entity) {
    return DatePeriod.builder()
        .from(entity.getValidFrom())
        .through(entity.getValidThrough())
        .build();
  }

  default UUID map(OverruleId overruleId) {
    return overruleId.id();
  }

  default OverruleId map(UUID uuid) {
    return OverruleId.from(uuid);
  }
}
