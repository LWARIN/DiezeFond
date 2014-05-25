package fr.sma.speadl.impl;

import java.util.ArrayList;
import java.util.List;

import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.MoveHandler;
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
		for (int i = 0; i < 10; i++) {
			robots.add(newRobot("#id" + i));
		}
	}

	@Override
	protected EnvironmentClock make_clock() {
		return new EnvironmentClockImpl();
	}

	@Override
	protected EnvironmentMove make_move() {
		return new EnvironmentMove() {

			@Override
			protected MoveHandler make_moveHandler() {
				return new MoveHandler() {

					@Override
					public void moveAgents() {
						for (Robot.Component robot : robots) {
							robot.robotActionHandler().triggerAction();
						}
					}
				};
			}

		};
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

	@Override
	protected ActionHandler make_actionHandler() {
		return new ActionHandler() {

			@Override
			public void triggerAction() {
				
			}
		};
	}
}
