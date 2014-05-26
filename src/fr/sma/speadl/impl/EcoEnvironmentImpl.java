package fr.sma.speadl.impl;

import java.util.ArrayList;
import java.util.List;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.speadl.ActionHandler;
import fr.sma.speadl.EnvironmentUpdater;
import fr.sma.speadl.MoveHandler;
import fr.sma.speadl.MoveTrigger;
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
					public List<Cell> getNeighbors(Position current) {
						System.out.println("GET NEIGHBORS");
						int x = current.getX();
						int y = current.getY();

						int xMin = (x - 3) <= 0 ? 0 : (x - 3);
						int xMax = (x + 3) >= EnvironmentUpdater.GRID_WIDTH - 1 ? EnvironmentUpdater.GRID_WIDTH - 1
								: (x + 3);

						int yMin = (y - 3) <= 0 ? 0 : (y - 3);
						int yMax = (y + 3) > EnvironmentUpdater.GRID_HEIGHT - 1 ? EnvironmentUpdater.GRID_HEIGHT - 1
								: (y + 3);

						List<Cell> neighbors = new ArrayList<Cell>();
						for (int i = xMin; i < xMax; i++) {
							for (int j = yMin; j < yMax; j++) {
								Cell cell = new Cell(new Position(i, j));
								cell.setState(requires().updateGrid().getState(i, j));
								neighbors.add(cell);
							}
						}

						return neighbors;
					}
				};
			}

			@Override
			protected MoveTrigger make_moveTrigger() {
				return new MoveTrigger() {

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
