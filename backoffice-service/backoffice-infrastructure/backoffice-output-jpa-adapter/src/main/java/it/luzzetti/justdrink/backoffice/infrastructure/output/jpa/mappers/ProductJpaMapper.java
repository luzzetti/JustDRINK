package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.ProductJpaEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface ProductJpaMapper {
  Product toDomain(ProductJpaEntity anEntity);

  ProductJpaEntity toEntity(Product domain);

  default UUID map(ProductId aTypedId) {
    return aTypedId.id();
  }

  default ProductId map(UUID uuid) {
    return ProductId.from(uuid);
  }
}
