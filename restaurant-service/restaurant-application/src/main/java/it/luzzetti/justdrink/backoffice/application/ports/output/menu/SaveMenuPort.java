package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;

public interface SaveMenuPort {
	Menu saveMenu(Menu aNewMenu);
	
}
