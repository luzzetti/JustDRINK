package it.luzzetti.justdrink.backoffice.application.services.worktime;

import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.CreateOverruleUseCase;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.GenerateOverruleIdPort;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.SaveWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Overrule;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import it.luzzetti.justdrink.backoffice.domain.shared.typed_ids.OverruleId;
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
  private final GenerateOverruleIdPort generateOverruleIdPort;

  @Override
  public Overrule createOpeningOverrule(CreateOpeningOverruleCommand command) {
    log.debug(() -> String.format("createOverrule(%s)", command));

    // Fetching resources
    Worktime theWorktime =
        findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());
    OverruleId theOverruleId = generateOverruleIdPort.nextOverruleIdentifier();

    // Use case
    Overrule theNewOverrule =
        Overrule.builder()
            .id(theOverruleId)
            .validity(command.validity())
            .dayOfWeek(command.dayOfWeek())
            .alternativeShift(command.alternativeShift())
            .closed(Boolean.FALSE)
            .build();

    theWorktime.addOverrule(theNewOverrule);

    // Saving data and crafting response
    Worktime theUpdatedWorktime = saveWorktimePort.saveWorktime(theWorktime);
    return theUpdatedWorktime.getOverruleById(theOverruleId);
  }

  @Override
  public Overrule createClosingOverrule(CreateClosingOverruleCommand command) {
    log.debug(() -> String.format("createOverrule(%s)", command));

    // Fetching resources
    Worktime theWorktime =
        findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());

    OverruleId theGeneratedOverruleId = generateOverruleIdPort.nextOverruleIdentifier();

    Overrule theNewOverrule =
        Overrule.builder()
            .id(theGeneratedOverruleId)
            .validity(command.validity())
            .dayOfWeek(command.dayOfWeek())
            .alternativeShift(null)
            .closed(Boolean.TRUE)
            .build();

    // Use case
    theWorktime.addOverrule(theNewOverrule);

    // Crafting response
    Worktime theUpdatedWorktime = saveWorktimePort.saveWorktime(theWorktime);
    return theUpdatedWorktime.getOverruleById(theGeneratedOverruleId);
  }
}
