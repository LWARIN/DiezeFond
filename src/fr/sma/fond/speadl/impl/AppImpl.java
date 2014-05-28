package fr.sma.fond.speadl.impl;

import fr.sma.fond.speadl.GridManager;
import Fond.App;
import Fond.AppGUI;
import Fond.Clock;
import Fond.Logger;
import Fond.Plant;

public class AppImpl extends App {

	@Override
	protected Plant make_plant() {
		return new PlantImpl();
	}

	@Override
	protected AppGUI make_gui() {
		return new AppGuiImpl(GridManager.GRID_WIDTH, GridManager.GRID_HEIGHT);
	}

	@Override
	protected Clock make_clock() {
		return new ClockImpl();
	}

	@Override
	protected Logger make_logger() {
		return new LoggerImpl();
	}
}
