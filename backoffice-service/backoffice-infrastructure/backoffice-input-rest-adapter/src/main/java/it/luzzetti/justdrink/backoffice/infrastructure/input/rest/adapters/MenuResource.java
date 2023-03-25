package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

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
public class MenuResource extends RepresentationModel<MenuResource> {
  private UUID id;

}
