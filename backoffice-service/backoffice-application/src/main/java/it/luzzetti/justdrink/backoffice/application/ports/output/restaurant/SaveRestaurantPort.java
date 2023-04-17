package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;

public interface SaveRestaurantPort {
	Restaurant saveRestaurant(Restaurant aNewRestaurant);
	
}
