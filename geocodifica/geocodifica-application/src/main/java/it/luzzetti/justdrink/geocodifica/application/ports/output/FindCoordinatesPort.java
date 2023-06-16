package it.luzzetti.justdrink.geocodifica.application.ports.output;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;
import java.util.Optional;

public interface FindCoordinatesPort {

    List<Coordinate> displayName(String addressName);

}
