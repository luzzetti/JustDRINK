package it.luzzetti.justdrink.geocodifica.application.ports.output;

import org.locationtech.jts.geom.Coordinate;

import java.util.Optional;

public interface FindCoordinatesPort {

    Optional<Coordinate> displayName(String addressName);

}
