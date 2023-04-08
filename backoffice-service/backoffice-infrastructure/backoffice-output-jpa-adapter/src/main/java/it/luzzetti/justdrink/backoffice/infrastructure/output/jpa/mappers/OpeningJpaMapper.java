package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.OpeningJpaEntity;
import org.mapstruct.Mapper;

@Mapper
public interface OpeningJpaMapper {

  OpeningJpaEntity toEntity(Opening domain);

  Opening toDomain(OpeningJpaEntity entity);
}
