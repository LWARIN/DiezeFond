package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sma.fond.core.Cell;
import fr.sma.fond.core.Position;
import fr.sma.fond.core.State;
import fr.sma.fond.speadl.ActionManager;
import fr.sma.fond.speadl.GridManager;
import fr.sma.fond.speadl.MemoryManager;
import Fond.RobotActionManager;
import Fond.RobotMemory;
import Fond.EcoRobot.Robot;

public class RobotImpl extends Robot {

	private String id;
	private Position currentPosition;
	private State oldGridState;
	private State goal;
	private State robotState;

	public RobotImpl(String id, Position position) {
		this.id = id;
		currentPosition = position;
		robotState = State.EMPTYROBOT;
		oldGridState = State.FREESPACE;
		switchGoalAndState();
	}

	public boolean equals(Object o) {
		System.out.println("EQUALS : " + ((RobotImpl) o).id == id);
		return ((RobotImpl) o).id == id;
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

			private Map<Cell, Integer> memoryCells = new HashMap<Cell, Integer>();
			private int nbInactive = 0;

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

						for (Map.Entry<Cell, Integer> memoryCell : memoryCells.entrySet()) {

							if (!cells.contains(memoryCell)) {
								memoryCells.remove(memoryCell);
							}
						}

						// Get only cells which are reachable in one move
						List<Cell> reachableCells = new ArrayList<Cell>();

						for (Cell cell : cells) {
							if (isCellReachable(cell.getPosition())) {

								if (!memoryCells.containsKey(cell)) {
									memoryCells.put(cell, 0);
								}

								if (cell.getState() == goal) {
									return cell.getPosition();
								}

								if (!isCellFull(cell.getState()) && memoryCells.get(cell) <= 2) {
									reachableCells.add(cell);
								}
							}
						}

						if (reachableCells.size() == 0) {
							nbInactive++;
							if (nbInactive > 2 && robotState == State.EMPTYROBOT) {
								eco_requires().gridProvider().setState(currentPosition.getX(), currentPosition.getY(), oldGridState);
								eco_provides().ecoRobotManager().killRobot(id);
							}
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
								memoryCells.put(cell, memoryCells.get(cell) + 1);
								return cell.getPosition();
							}
						}

						// Default behaviour
						for (Cell cell : reachableCells) {
							if (defaultBehaviour == UP) {

								if (cell.getPosition().getY() < currentPosition.getY()) {
									memoryCells.put(cell, memoryCells.get(cell) + 1);
									return cell.getPosition();
								}
							} else {
								if (cell.getPosition().getY() > currentPosition.getY()) {
									memoryCells.put(cell, memoryCells.get(cell) + 1);
									return cell.getPosition();
								}
							}
						}

						// Fool bahaviour
						Cell foolCell = reachableCells.get(0);
						memoryCells.put(foolCell, memoryCells.get(foolCell) + 1);
						return foolCell.getPosition();
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

						List<Cell> neighbors = eco_requires().gridProvider().getNeighbors(currentPosition);

						Position nextPosition = requires().memoryManager().getNextPosition(neighbors, goal, currentPosition);

						if (nextPosition == null) {
							eco_requires().log().warning("RobotImpl #" + id, "Robot can not move " + currentPosition);
						} else {
							State state = eco_requires().gridProvider().getState(nextPosition.getX(), nextPosition.getY());
							if (state == goal) {
								eco_requires().log().info("RobotImpl #" + id, "Robot has reached its goal in : " + nextPosition);
								switchGoalAndState();
							} else {
								eco_requires().log().info("RobotImpl #" + id, "Robot has moved on : " + nextPosition);
							}
							eco_requires().gridProvider().setState(currentPosition.getX(), currentPosition.getY(), oldGridState);
							oldGridState = eco_requires().gridProvider().getState(nextPosition.getX(), nextPosition.getY());
							eco_requires().gridProvider().setState(nextPosition.getX(), nextPosition.getY(), robotState);
							currentPosition = nextPosition;
						}

					}

					@Override
					public String getId() {
						return id;
					}
				};
			}
		};
	}
}
