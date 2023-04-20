package it.luzzetti.justdrink.backoffice.application.ports.output.restaurant;

import it.luzzetti.justdrink.backoffice.domain.FileImage;

public interface SaveLogoRestaurantPort {

  String saveLogo(FileImage image);

}
