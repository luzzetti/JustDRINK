package it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime;

import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOpeningUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOpeningUseCase.CreateOpeningCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOverruleUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOverruleUseCase.CreateClosingOverruleCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOverruleUseCase.CreateOpeningOverruleCommand;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.ShowWorktimeQuery;
import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.ShowWorktimeQuery.ShowWorktimeCommand;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.RestaurantId;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.ClosingOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningCreationRequest;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningOverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OpeningResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.OverruleResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.adapters.worktime.dto.WorktimeResource;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.OpeningWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.OverruleWebMapper;
import it.luzzetti.justdrink.backoffice.infrastructure.input.rest.mappers.WorktimeWebMapper;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * This controller, contains examples of Polymorphism API.
 * <p>
 * Theory:
 * Api Design Pattern - Part 4 - Chapter 16
 * <p>
 * Implementation:
 * <a href="https://totheroot.nl/content/applying-polymorphism-to-a-rest-api-using-spring-boot">...</a>
 */

@RestController
@RequestMapping("/api/1.0/restaurants/{restaurantId}/worktime")
@Log4j2
@RequiredArgsConstructor
/*
 * TODO: Rimuovere ASSOLUTAMENTE questa configurazione crossorigin da qui, una volta aggiunte
 *  le corrette configurazioni WEB
 */
@CrossOrigin("*")
public class WorktimeRestControllerAdapter {

  // UseCases
  private final CreateOpeningUseCase createOpeningUseCase;
  private final CreateOverruleUseCase createOverruleUseCase;

  // Queries
  private final ShowWorktimeQuery showWorktimeQuery;

  // Mappers
  private final WorktimeWebMapper worktimeWebMapper;
  private final OpeningWebMapper openingWebMapper;
  private final OverruleWebMapper overruleWebMapper;

  @GetMapping
  public ResponseEntity<WorktimeResource> getWorktime(@PathVariable UUID restaurantId) {
    // Parsing parameters from HTTP
    var command =
        ShowWorktimeCommand.builder().restaurantId(RestaurantId.from(restaurantId)).build();

    // Calling Use-Case
    Worktime theFoundWorktime = showWorktimeQuery.showWorktime(command);

    // Crafting response
    WorktimeResource resource = worktimeWebMapper.toResource(theFoundWorktime);
    return ResponseEntity.ok(resource);
  }

  @PostMapping("/openings")
  public ResponseEntity<OpeningResource> createOpening(
      @PathVariable UUID restaurantId, @RequestBody @Valid OpeningCreationRequest request) {

    var command =
        CreateOpeningCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .dayOfWeek(request.dayOfWeek())
            .openTime(request.openTime())
            .closeTime(request.closeTime())
            .build();

    Opening theCreatedOpening = createOpeningUseCase.createOpening(command);

    OpeningResource response = openingWebMapper.toResource(theCreatedOpening);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/overrules")
  public ResponseEntity<OverruleResource> createOverrule(
      @PathVariable UUID restaurantId, @RequestBody @Valid OverruleResource request) {

    Overrule theCreatedOverrule;
    if (request instanceof OpeningOverruleResource openingRequest) {
      theCreatedOverrule = handleOpeningOverruleCreation(openingRequest, restaurantId);
    } else if (request instanceof ClosingOverruleResource closingRequest) {
      theCreatedOverrule = handleClosingOverruleCreation(closingRequest, restaurantId);
    } else {
      throw new IllegalStateException("This should NEVER happens, honestly");
    }

    OverruleResource response = overruleWebMapper.toResource(theCreatedOverrule);
    return ResponseEntity.ok(response);
  }

  private Overrule handleClosingOverruleCreation(
      ClosingOverruleResource closingRequest, UUID restaurantId) {
    var command =
        CreateClosingOverruleCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .validFrom(closingRequest.getValidFrom())
            .validThrough(closingRequest.getValidThrough())
            .dayOfWeek(closingRequest.getDayOfWeek())
            .build();

    return createOverruleUseCase.createClosingOverrule(command);
  }

  private Overrule handleOpeningOverruleCreation(
      OpeningOverruleResource openingRequest, UUID restaurantId) {
    var command =
        CreateOpeningOverruleCommand.builder()
            .restaurantId(RestaurantId.from(restaurantId))
            .validFrom(openingRequest.getValidFrom())
            .validThrough(openingRequest.getValidThrough())
            .dayOfWeek(openingRequest.getDayOfWeek())
            .alternativeOpenTime(openingRequest.getAlternativeOpenTime())
            .alternativeCloseTime(openingRequest.getAlternativeCloseTime())
            .build();

    return createOverruleUseCase.createOpeningOverrule(command);
  }
}
