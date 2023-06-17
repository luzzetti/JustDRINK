package it.luzzetti.justdrink.geocodifica.application.ports.output;

import it.luzzetti.justdrink.geocodifica.domain.aggregates.address.Place;
import it.luzzetti.justdrink.geocodifica.domain.shared.GeoparsedQuery;

public interface FindPlacesPort {

    GeoparsedQuery<Place> findPlacesFromAddress(String address);

}
