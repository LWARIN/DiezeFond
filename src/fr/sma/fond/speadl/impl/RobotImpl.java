package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.core.State;
import fr.sma.fond.speadl.ActionManager;
import fr.sma.fond.speadl.GridManager;
import fr.sma.fond.speadl.MemoryManager;
import Fond.RobotActionManager;
import Fond.RobotMemory;
import Fond.EcoRobot.Robot;

public class RobotImpl extends Robot {

	private final static Logger LOGGER = Logger.getLogger(RobotImpl.class.getName());
	private String id;
	private Position currentPosition;
	private State goal;
	private State robotState;

	public RobotImpl(String id, Position position) {
		this.id = id;
		currentPosition = position;
		robotState = State.EMPTYROBOT;
		switchGoalAndState();
	}

	private void switchGoalAndState() {
		if (goal == State.DESTINATION) {
			goal = State.EXPEDITION;
			robotState = State.EMPTYROBOT;
		} else {
			goal = State.DESTINATION;
			robotState = State.ROBOT;
		}
	}

	@Override
	protected RobotMemory make_memory() {
		return new RobotMemory() {

			@Override
			protected MemoryManager make_memoryManager() {
				return new MemoryManager() {

					private final boolean UP = false;
					private final boolean DOWN = true;

					private boolean defaultBehaviour = DOWN;

					private boolean isCellReachable(Position cellPosition) {
						int cellX = cellPosition.getX();
						int currentX = currentPosition.getX();
						boolean result = cellX - 1 == currentX || cellX == currentX || cellX + 1 == currentX;
						int cellY = cellPosition.getY();
						int currentY = currentPosition.getY();
						result = result && (cellY - 1 == currentY || cellY == currentY || cellY + 1 == currentY);
						return result;
					}

					private boolean isCellFull(State state) {
						return state == State.OBSTACLE || state == State.ROBOT || state == State.EMPTYROBOT;
					}

					@Override
					public Position getNextPosition(List<Cell> cells, State goal, Position current) {

						// Get only cells which are reachable in one move
						List<Cell> reachableCells = new ArrayList<Cell>();

						for (Cell cell : cells) {
							if (isCellReachable(cell.getPosition())) {
								if (cell.getState() == goal) {
									return cell.getPosition();
								}

								if (!isCellFull(cell.getState())) {
									reachableCells.add(cell);
								}
							}
						}

						if (reachableCells.size() == 0) {
							return null;
						}

						// Try to move on the goal direction

						if (currentPosition.getY() == 0) {
							defaultBehaviour = DOWN;
						} else if (currentPosition.getY() == GridManager.GRID_HEIGHT - 1) {
							defaultBehaviour = UP;
						}

						for (Cell cell : reachableCells) {
							boolean result = (goal == State.DESTINATION && cell.getPosition().getX() > currentPosition.getX())
									|| (goal == State.EXPEDITION && cell.getPosition().getX() < currentPosition.getX());

							if (defaultBehaviour == UP) {
								result = result && (cell.getPosition().getY() <= currentPosition.getY());
							} else {
								result = result && (cell.getPosition().getY() >= currentPosition.getY());
							}

							if (result) {
								return cell.getPosition();
							}
						}

						// Default behaviour
						for (Cell cell : reachableCells) {
							if (defaultBehaviour == UP) {
								if (cell.getPosition().getY() < currentPosition.getY()) {
									return cell.getPosition();
								}
							} else {
								if (cell.getPosition().getY() > currentPosition.getY()) {
									return cell.getPosition();
								}
							}
						}

						// Fool bahaviour
						return reachableCells.get(0).getPosition();
					}
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
						LOGGER.info("I SHALL MOVE " + id + " FROM POSITION : " + currentPosition + " - Goal : " + goal);

						List<Cell> neighbors = eco_requires().gridProvider().getNeighbors(currentPosition);

						Position nextPosition = requires().memoryManager().getNextPosition(neighbors, goal,
								currentPosition);

						if (nextPosition == null) {
							LOGGER.warning(id + " : I cannot move : nothing do : " + currentPosition);
						} else {
							State state = eco_requires().gridProvider().getState(nextPosition.getX(),
									nextPosition.getY());
							if (state == goal) {
								LOGGER.info(id + " : I have found my goal in : " + nextPosition);
								switchGoalAndState();
							} else {
								LOGGER.info(id + " : Move on : " + nextPosition);
							}
							eco_requires().gridProvider().setState(currentPosition.getX(), currentPosition.getY(),
									State.FREESPACE);
							eco_requires().gridProvider()
									.setState(nextPosition.getX(), nextPosition.getY(), robotState);
							currentPosition = nextPosition;
						}

					}
				};
			}
		};
	}
}
