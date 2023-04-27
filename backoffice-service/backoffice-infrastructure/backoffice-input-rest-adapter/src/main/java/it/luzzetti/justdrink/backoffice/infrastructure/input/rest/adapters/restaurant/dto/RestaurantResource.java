package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.restaurant.dto;

import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import java.util.Set;
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
public class RestaurantResource extends RepresentationModel<RestaurantResource> {
  private UUID id;
  private String name;
  private Address address;
  private boolean enabled;
  private Set<CuisineResource> cuisines;
}
