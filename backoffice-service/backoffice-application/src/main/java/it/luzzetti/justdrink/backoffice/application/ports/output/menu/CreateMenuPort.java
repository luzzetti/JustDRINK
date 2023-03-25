package it.luzzetti.justdrink.backoffice.application.ports.output.menu;

import it.luzzetti.justdrink.backoffice.domain.aggregates.menu.Menu;

public interface CreateMenuPort {
	Menu createMenu(Menu aNewMenu);
	
}
