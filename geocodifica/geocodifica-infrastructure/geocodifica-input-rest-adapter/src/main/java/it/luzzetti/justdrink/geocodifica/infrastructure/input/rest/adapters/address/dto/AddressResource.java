package it.luzzetti.justdrink.geocodifica.infrastructure.input.rest.adapters.address.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AddressResource extends RepresentationModel<AddressResource> {

    private String displayName;
    private Coordinate coordinate;

    public static interface AddressRestController {

      ResponseEntity<AddressResource> geocodifica(@PathVariable String displayName);
    }
}
