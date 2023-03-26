package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;


import java.util.UUID;

public interface DeleteRestaurantPort {
  	
void deleteRestaurantById(UUID restaurantId);
}
