package it.luzzetti.justdrink.backoffice.application.ports.output.worktime;

import it.luzzetti.justdrink.backoffice.domain.aggregates.worktime.Worktime;

public interface SaveWorktimePort {

  Worktime saveWorktime(Worktime aNewWorktime);
}
