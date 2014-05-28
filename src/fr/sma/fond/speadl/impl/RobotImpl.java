package fr.sma.fond.speadl.impl;

import java.util.List;
import java.util.logging.Logger;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.fond.speadl.ActionManager;
import fr.sma.fond.speadl.MemoryManager;
import Fond.RobotActionManager;
import Fond.RobotMemory;
import Fond.EcoRobot.Robot;

public class RobotImpl extends Robot {

	private final static Logger LOGGER = Logger.getLogger(RobotImpl.class.getName());
	private String id;
	private Position currentPosition;

	public RobotImpl(String id, Position position) {
		this.id = id;
		currentPosition = position;
	}

	@Override
	protected RobotMemory make_memory() {
		return new RobotMemory() {

			@Override
			protected MemoryManager make_memoryManager() {
				return new MemoryManager() {

				};
			}

		};
	}

	@Override
	protected RobotActionManager make_actionManager() {
		return new RobotActionManager() {

			@Override
			protected ActionManager make_actionManager() {
				return new ActionManager() {

					@Override
					public void move() {
						LOGGER.info("I SHALL MOVE #" + id + " FROM POSITION : " + currentPosition);

						List<Cell> neighbors = eco_requires().gridProvider().getNeighbors(currentPosition);
						LOGGER.info("#" + id + ": My neighbors are : ");
						for (Cell cell : neighbors) {
							LOGGER.info(cell.getPosition() + " - " + cell.getState());
						}
					}

				};
			}

		};
	}
}
