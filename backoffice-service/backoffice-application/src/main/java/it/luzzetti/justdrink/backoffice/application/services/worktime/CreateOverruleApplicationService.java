package it.luzzetti.justdrink.backoffice.application.services.worktime;

import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOverruleUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateOverruleApplicationService implements CreateOverruleUseCase {

  // Ports
  private final FindWorktimePort findWorktimePort;
  private final SaveWorktimePort saveWorktimePort;

  @Override
  public Overrule createOpeningOverrule(CreateOpeningOverruleCommand command) {
    log.debug(() -> String.format("createOverrule(%s)", command));

    // Fetching resources
    Worktime theWorktime =
        findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());

    Overrule theNewOverrule =
        Overrule.builder()
            .validFrom(command.validFrom())
            .validThrough(command.validThrough())
            .dayOfWeek(command.dayOfWeek())
            .alternativeOpenTime(command.alternativeOpenTime())
            .alternativeCloseTime(command.alternativeCloseTime())
            .closed(Boolean.FALSE)
            .build();

    // Use case
    theWorktime.addOverrule(theNewOverrule);

    // Crafting response
    Worktime theUpdatedWorktime = saveWorktimePort.saveWorktime(theWorktime);
    return theUpdatedWorktime.getLastCreatedOverrule();
  }

  @Override
  public Overrule createClosingOverrule(CreateClosingOverruleCommand command) {
    log.debug(() -> String.format("createOverrule(%s)", command));

    // Fetching resources
    Worktime theWorktime =
        findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());

    Overrule theNewOverrule =
        Overrule.builder()
            .validFrom(command.validFrom())
            .validThrough(command.validThrough())
            .dayOfWeek(command.dayOfWeek())
            .alternativeOpenTime(null)
            .alternativeCloseTime(null)
            .closed(Boolean.TRUE)
            .build();

    // Use case
    theWorktime.addOverrule(theNewOverrule);

    // Crafting response
    Worktime theUpdatedWorktime = saveWorktimePort.saveWorktime(theWorktime);
    return theUpdatedWorktime.getLastCreatedOverrule();
  }
}
