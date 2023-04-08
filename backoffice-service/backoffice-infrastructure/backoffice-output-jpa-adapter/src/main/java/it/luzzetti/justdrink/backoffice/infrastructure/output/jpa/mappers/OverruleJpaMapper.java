package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OverruleJpaEntity;
import org.mapstruct.Mapper;

@Mapper
public interface OverruleJpaMapper {

  OverruleJpaEntity toEntity(Overrule domain);

  Overrule toDomain(OverruleJpaEntity entity);
}
