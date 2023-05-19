package it.luzzetti.justdrink.backoffice.application.services.worktime;

import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOpeningUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateOpeningIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Opening;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OpeningId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateOpeningApplicationService implements CreateOpeningUseCase {

  // Ports
  private final FindWorktimePort findWorktimePort;
  private final GenerateOpeningIdPort generateOpeningIdPort;
  private final SaveWorktimePort saveWorktimePort;

  @Override
  @Transactional
  public Opening createOpening(@Valid CreateOpeningCommand command) {
    log.debug(() -> "createOpening(%s)".formatted(command));

    // Fetching resources
    Worktime theWorktime =
        findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());

    // Use case
    OpeningId theGeneratedOpeningId = generateOpeningIdPort.nextOpeningIdentifier();

    Opening theNewOpening =
        Opening.builder()
            .id(theGeneratedOpeningId)
            .dayOfWeek(command.dayOfWeek())
            .shift(command.shift())
            .build();

    theWorktime.addOpening(theNewOpening);

    // Crafting response
    Worktime theUpdatedWorktime = saveWorktimePort.saveWorktime(theWorktime);
    return theUpdatedWorktime.getOpeningById(theGeneratedOpeningId);
  }
}
