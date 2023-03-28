package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Product;
import it.luzzetti.justdrink.backoffice.domain.shared.ProductId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.ProductResource;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper
public interface ProductWebMapper {
  ProductResource toResource(Product product);

  default UUID map(ProductId value){
    return value.id();
  }
}
