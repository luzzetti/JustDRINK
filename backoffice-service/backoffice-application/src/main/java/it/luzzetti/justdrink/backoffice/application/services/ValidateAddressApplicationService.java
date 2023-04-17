package it.luzzetti.justdrink.backoffice.application.services;

import it.luzzetti.justdrink.backoffice.application.ports.input.ValidateAddressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.FindCoordinatesPort;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ValidateAddressApplicationService implements ValidateAddressUseCase {

  private final FindCoordinatesPort findCoordinatesPort;

  @Override
  public Address validateAddress(ValidateAddressCommand command) {

    // Implementare Cache
    String addressNameToGeocode = command.addressName();
    Optional<Coordinates> userProvidedCoordinates = command.coordinates();

    Coordinates coordinates =
        // Usa le coordinate fornite dall'utente...
        userProvidedCoordinates
            // Se non ci sono, cerca nel db locale
            .or(() -> repositoryDiQuestoServizio(addressNameToGeocode))
            // Se non ci sono, chiede al nostro servizio di geocodifica
            .or(() -> repositoryRemotoDiGeoservice(addressNameToGeocode))
            // Se non ci sono, chiede al servizio a pagamento
            .or(() -> findCoordinatesPort.findCoordinatesByAddressName(addressNameToGeocode))
            // Se non ci sono, dio cane, esplode
            .orElseThrow(() -> new IllegalArgumentException(""));

    return Address.builder().name(addressNameToGeocode).coordinates(coordinates).build();
  }

  // Metodo solo di test! Non usare perché lo eliminerò
  public Optional<Coordinates> repositoryDiQuestoServizio(String addressName) {
    return Optional.empty();
  }

  // Metodo solo di test! Non usare perché lo eliminerò
  public Optional<Coordinates> repositoryRemotoDiGeoservice(String addressName) {
    return Optional.empty();
  }
}
