package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.owner;

import it.luzzetti.justdrink.backoffice.application.ports.input.owner.RegisterOwnerUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.owner.RegisterOwnerUseCase.RegisterOwnerCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.owner.ShowMyselfQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.owner.ShowMyselfQuery.ShowMyselfCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.owner.Owner;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * This is a 'Singleton sub-resource' like shown in 'API Design Patterns'
 */

@RestController
@RequestMapping("/api/1.0/owners")
@Log4j2
@RequiredArgsConstructor
public class OwnerRestControllerAdapter {

  // UseCases
  private final RegisterOwnerUseCase registerOwnerUseCase;

  // Queries
  private final ShowMyselfQuery showMyselfQuery;

  @PostMapping
  public ResponseEntity<Owner> registerOwner(@RequestBody @Valid RegisterOwnerRequest request) {
    // Fetching Data
    var command = RegisterOwnerCommand.builder().email(request.email()).build();

    // Calling UseCase
    Owner theNewOwner = registerOwnerUseCase.registerOwner(command);

    // Crafting a Response
    return ResponseEntity.ok(theNewOwner);
  }

  @GetMapping("/myself")
  public ResponseEntity<Owner> showMyself() {
    // Fetching Data
    var command = ShowMyselfCommand.builder().build();

    // Calling the Query
    Owner myself = showMyselfQuery.showMyself(command);

    // Crafting a response
    return ResponseEntity.ok(myself);
  }

  public record RegisterOwnerRequest(@NotNull String email) {}
}
