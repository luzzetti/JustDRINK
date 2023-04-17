package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters;

import it.luzzetti.justdrink.backoffice.application.ports.input.ValidateAddressUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.ValidateAddressUseCase.ValidateAddressCommand;
import it.luzzetti.justdrink.backoffice.domain.vo.Address;
import it.luzzetti.justdrink.backoffice.domain.vo.Coordinates;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/addresses")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class AddressRestControllerAdapter {

  // UseCases
  private final ValidateAddressUseCase validateAddressUseCase;

  /*
   * FIXME: this is just a PoC (Proof of concept) and it's not ready for production
   */

  @GetMapping
  public ResponseEntity<Address> validateAddress(
      @RequestParam Optional<String> addressName, @RequestParam Optional<Coordinates> coordinates) {

    var command =
        ValidateAddressCommand.builder()
            .addressName(addressName.orElse("Via cairoli 17, 01100, Viterbo"))
            .coordinates(coordinates)
            .build();

    Address address = validateAddressUseCase.validateAddress(command);

    return ResponseEntity.ok(address);
  }
}
