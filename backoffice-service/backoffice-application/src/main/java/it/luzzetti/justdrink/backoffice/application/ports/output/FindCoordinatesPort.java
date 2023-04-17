package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.Optional;

public interface FindCoordinatesPort {

  Optional<Coordinates> findCoordinatesByAddressName(String addressName);
}
