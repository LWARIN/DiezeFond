package fr.sma.fond.speadl.impl;

import Fond.App;
import Fond.Clock;

import Fond.Logger;

import Fond.Gui;

import Fond.Plant;
import fr.sma.fond.speadl.GridManager;

public class AppImpl extends App {

	@Override
	protected Plant make_plant() {
		return new PlantImpl();
	}

	@Override
	protected Gui make_gui() {
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
