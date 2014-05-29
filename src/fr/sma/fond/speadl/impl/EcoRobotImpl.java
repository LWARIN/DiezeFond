package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;
import fr.sma.core.Position;
import fr.sma.fond.speadl.EcoRobotManager;
import Fond.EcoRobot;

public class EcoRobotImpl extends EcoRobot {

	private List<Robot.Component> robots;

	@Override
	protected void start() {
		robots = new ArrayList<Robot.Component>();
		for (int i = 0; i < 10; i++) {
			robots.add(newRobot("#id" + i, new Position(0, 0)));
		}
	}

	@Override
	protected EcoRobotManager make_ecoRobotManager() {
		return new EcoRobotManager() {

			@Override
			public void moveRobots() {
				requires().log().info("EcoRobotImpl", "##### START MOVING ROBOTS #####");
				for (Robot.Component robot : robots) {
					robot.robotActionHandler().move();
				}
				requires().log().info("EcoRobotImpl", "##### END MOVING ROBOTS #####");
			}

		};
	}

	@Override
	protected Robot make_Robot(String id, Position position) {
		return new RobotImpl(id, position);
	}

}
