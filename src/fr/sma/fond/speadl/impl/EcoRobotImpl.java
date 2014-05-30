package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.sma.fond.core.Position;
import fr.sma.fond.speadl.EcoRobotManager;
import Fond.EcoRobot;

public class EcoRobotImpl extends EcoRobot {

	private List<Robot.Component> robots;
	private List<String> idsToRemove;

	@Override
	protected void start() {
		robots = new ArrayList<Robot.Component>();
		for (int i = 0; i < 50; i++) {
			Position startPosition = requires().gridProvider().getRandomFreeCell();
			robots.add(newRobot("#id" + i, startPosition));
		}

		idsToRemove = new ArrayList<String>();
	}

	@Override
	protected EcoRobotManager make_ecoRobotManager() {
		return new EcoRobotManager() {

			@Override
			public void moveRobots() {
				requires().log().info("EcoRobotImpl", "##### START MOVING ROBOTS #####");

				Iterator<Robot.Component> robotIterator = robots.iterator();
				while (robotIterator.hasNext()) {
					Robot.Component robot = robotIterator.next();

					String id = robot.robotActionHandler().getId();
					if (idsToRemove.contains(id)) {
						requires().log().info("EcoRobotImpl", "Robot #" + id + " has been removed");
						idsToRemove.remove(id);
						robotIterator.remove();
					} else {
						robot.robotActionHandler().move();
					}
				}

				requires().log().info("EcoRobotImpl", "##### END MOVING ROBOTS #####");
			}

			@Override
			public void killRobot(String id) {
				requires().log().info("EcoRobotImpl", "Robot #" + id + " committed suicide");
				idsToRemove.add(id);
			}

		};
	}

	@Override
	protected Robot make_Robot(String id, Position position) {
		return new RobotImpl(id, position);
	}

}
