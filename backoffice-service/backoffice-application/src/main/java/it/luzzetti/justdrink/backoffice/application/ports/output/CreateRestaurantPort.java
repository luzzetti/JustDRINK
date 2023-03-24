package it.luzzetti.justdrink.backoffice.application.ports.output;

import it.luzzetti.justdrink.backoffice.domain.aggregates.restaurant.Restaurant;

public interface CreateRestaurantPort { 
	Restaurant createRestaurant(Restaurant aNewRestaurant);
	
}
