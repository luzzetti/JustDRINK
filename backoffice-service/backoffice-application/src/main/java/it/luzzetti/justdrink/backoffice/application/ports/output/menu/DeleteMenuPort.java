package it.luzzetti.justdrink.backoffice.application.ports.output.menu;


import java.util.UUID;

public interface DeleteMenuPort {
void deleteMenuByRestaurantId(UUID restaurantId);
}
