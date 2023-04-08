package it.luzzetti.justdrink.backoffice.application.services.worktime;

import it.luzzetti.justdrink.backoffice.application.ports.input.worktime.ShowWorktimeQuery;
import it.luzzetti.justdrink.backoffice.application.ports.output.worktime.FindWorktimePort;
import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShowWorktimeApplicationService implements ShowWorktimeQuery {

  private final FindWorktimePort findWorktimePort;

  @Override
  public Worktime showWorktime(ShowWorktimeCommand command) {
    log.debug(() -> String.format("showWorktime(%s)", command));

    return findWorktimePort.findWorktimeByRestaurantIdMandatory(command.restaurantId());
  }
}
