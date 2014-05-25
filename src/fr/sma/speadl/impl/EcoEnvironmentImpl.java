package fr.sma.speadl.impl;

import java.util.ArrayList;
import java.util.List;

import fr.sma.speadl.ActionHandler;
import DiezeFond.AppGUI;
import DiezeFond.EcoEnvironment;
import DiezeFond.EnvironmentClock;
import DiezeFond.EnvironmentGUI;
import DiezeFond.EnvironmentMove;
import DiezeFond.GridManager;

public class EcoEnvironmentImpl extends EcoEnvironment {

	private List<Robot.Component> robots;

	public EcoEnvironmentImpl() {
		robots = new ArrayList<Robot.Component>();

	}

	@Override
	protected ActionHandler make_actionHandler() {
		return new ActionHandlerImpl();
	}

	@Override
	protected EnvironmentClock make_clock() {
		return new EnvironmentClockImpl();
	}

	@Override
	protected EnvironmentMove make_move() {
		return new EnvironmentMoveImpl();
	}

	@Override
	protected GridManager make_gridManager() {
		return new GridManagerImpl();
	}

	@Override
	protected EnvironmentGUI make_environmentGui() {
		return new EnvironmentGuiImpl();
	}

	@Override
	protected AppGUI make_appGui() {
		return new AppGuiImpl();
	}

	@Override
	protected Robot make_Robot(String id) {
		return new RobotImpl(id);
	}
}
