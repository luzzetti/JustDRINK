package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.menu.dto;

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
public class MenuSectionResource extends RepresentationModel<MenuSectionResource> {
  private UUID id;
  private String title;

}
