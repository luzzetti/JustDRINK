package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.application.ports.input.restaurant.UploadLogoUsecase.UploadLogoRetaurantCommand;

public interface UpdateFileRestaurantPort {

  public String updateLogo(UploadLogoRetaurantCommand command);


}
