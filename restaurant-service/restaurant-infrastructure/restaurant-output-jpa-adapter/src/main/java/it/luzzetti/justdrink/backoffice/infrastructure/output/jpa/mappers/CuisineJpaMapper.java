package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.vo.Cuisine;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.CuisineJpaEmbeddable;
import org.mapstruct.Mapper;

@Mapper
public interface CuisineJpaMapper {

  Cuisine toDomain(CuisineJpaEmbeddable embeddable);

  CuisineJpaEmbeddable toEntity(Cuisine domain);
}
