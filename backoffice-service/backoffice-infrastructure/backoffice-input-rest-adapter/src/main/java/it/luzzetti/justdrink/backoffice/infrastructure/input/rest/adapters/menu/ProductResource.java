package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProductResource extends RepresentationModel<ProductResource> {
  private UUID id;
  private String name;
  private BigDecimal price;
}
