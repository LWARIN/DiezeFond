package fr.sma.speadl.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.MemoryHandler;
import DiezeFond.EcoEnvironment.Robot;
import DiezeFond.RobotActionManager;
import DiezeFond.RobotMemory;

public class RobotImpl extends Robot {

	private final static Logger LOGGER = Logger.getLogger(RobotImpl.class.getName());
	private String id;
	private Position currentPosition;
	private Position expectedPosition;

	public RobotImpl(String id) {
		this.id = id;
		this.currentPosition = new Position();
	}

	@Override
	protected RobotMemory make_memory() {
		return new RobotMemory() {

			@Override
			protected MemoryHandler make_memoryHandler() {
				return new MemoryHandler() {

					@Override
					public Cell[][] getMemorizedInformation(int x, int y) {
						return null;
					}
				};
			}
		};
	}

	@Override
	protected RobotActionManager make_actionManager() {
		return new RobotActionManager() {

			@Override
			protected ActionHandler make_actionHandler() {
				return new ActionHandler() {

					@Override
					public void triggerAction() {
						LOGGER.log(Level.INFO, "Robot #" + id + " triggering scheduled action !");
						System.out.println(currentPosition);
						
						List<Cell> neighbors = eco_provides().moveHandler().getNeighbors(currentPosition);
						System.out.println("NEIGHBORS...");
						for(Cell cell : neighbors) {
							System.out.println(cell.getPosition() + " - " + cell.getState());
						}
					}

				};
			}

		};
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Position getExpectedPosition() {
		return expectedPosition;
	}

	public void setExpectedPosition(Position expectedPosition) {
		this.expectedPosition = expectedPosition;
	}
}
