package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.fond.speadl.EcoRobotManager;
import Fond.EcoRobot;

public class EcoRobotImpl extends EcoRobot {

	private final static Logger LOGGER = Logger.getLogger(EcoRobotImpl.class.getName());

	private List<Robot.Component> robots;

	public EcoRobotImpl() {
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
				LOGGER.info("Move all robots");
				List<Cell> neighbors = requires().gridProvider().getNeighbors(new Position(0, 0));
				for(Cell cell : neighbors) {
					System.out.println("IT WORKS");
				}
				
				for (Robot.Component robot : robots) {
					// IT SHOULD WORK...	
					//	robot.robotActionHandler().move();
				}
			}

		};
	}

	@Override
	protected Robot make_Robot(String id, Position position) {
		return new RobotImpl(id, position);
	}

}
