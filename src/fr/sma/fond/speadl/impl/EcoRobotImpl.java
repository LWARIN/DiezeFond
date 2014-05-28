package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.sma.core.Position;
import fr.sma.fond.speadl.EcoRobotManager;
import Fond.EcoRobot;

public class EcoRobotImpl extends EcoRobot {

	private final static Logger LOGGER = Logger.getLogger(EcoRobotImpl.class.getName());

	private List<Robot.Component> robots;

	@Override
	protected void start() {
		robots = new ArrayList<Robot.Component>();
		for (int i = 0; i < 1; i++) {
			robots.add(newRobot("#id" + i, new Position(95, 0)));
		}
	}

	@Override
	protected EcoRobotManager make_ecoRobotManager() {
		return new EcoRobotManager() {

			@Override
			public void moveRobots() {
				LOGGER.info("Move all robots");	
				for (Robot.Component robot : robots) {
					robot.robotActionHandler().move();
				}
			}

		};
	}

	@Override
	protected Robot make_Robot(String id, Position position) {
		return new RobotImpl(id, position);
	}

}
