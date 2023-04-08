package it.luzzetti.justdrink.backoffice.application.ports.output.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.RestaurantId;

public interface FindWorktimePort {
  Worktime findWorktimeByRestaurantIdMandatory(RestaurantId restaurantId);
}
