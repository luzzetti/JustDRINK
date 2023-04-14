package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.shared.value_objects.Timeslot;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OpeningJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface OpeningJpaMapper {

  @Mapping(target = "openTime", source = "shift.from")
  @Mapping(target = "closeTime", source = "shift.through")
  OpeningJpaEntity toEntity(Opening domain);

  @Mapping(target = "shift", source = ".", qualifiedByName = "toShiftFromEntity")
  Opening toDomain(OpeningJpaEntity entity);

  @Named("toShiftFromEntity")
  default Timeslot toShift(OpeningJpaEntity entity) {
    return Timeslot.builder().from(entity.getOpenTime()).through(entity.getCloseTime()).build();
  }
}
