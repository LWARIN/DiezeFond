package fr.sma.speadl.impl;

import fr.sma.core.Cell;
import fr.sma.core.Robot;
import fr.sma.core.State;
import fr.sma.speadl.EnvironmentUpdater;
import fr.sma.speadl.GridDataProvider;
import fr.sma.speadl.GridUpdater;
import DiezeFond.Ecosystem.Environment;
import DiezeFond.EnvironmentClock;
import DiezeFond.EnvironmentMove;
import DiezeFond.GridManager;

public class EnvironmentImpl extends Environment {

	private Cell[][] grid;
	private int obstacleX;
	private int obstacleWidth;

	public EnvironmentImpl() {
		grid = new Cell[EnvironmentUpdater.GRID_WIDTH][EnvironmentUpdater.GRID_HEIGHT];
		for (int i = 0; i < EnvironmentUpdater.GRID_WIDTH; ++i) {
			for (int j = 0; j < EnvironmentUpdater.GRID_HEIGHT; ++j) {
				grid[i][j] = new Cell();
			}
		}
		
		int nbRobots = 30;
		for (int i = 0; i < nbRobots; ++i) {					
			try {
				new Thread(new Robot()).start();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected GridDataProvider make_dataProvider() {
		return new GridDataProvider() {

			@Override
			public State getState(int x, int y) {
				return grid[x][y].getState();
			}

			@Override
			public void setState(int x, int y, State state) {
				grid[x][y].setState(state);
			}
		};
	}

	@Override
	protected EnvironmentClock make_clock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EnvironmentMove make_move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GridManager make_gridManager() {
		return new GridManager() {

			@Override
			protected EnvironmentUpdater make_updateEnvironment() {
				return new EnvironmentUpdater() {
					@Override
					public void setExpeditionArea(int x, int y, int width,
							int height) {
						for (int i = x; i < x + width; i++) {
							for (int j = y; j < y + height; j++) {
								grid[i][j].setState(State.EXPEDITION);
							}
						}
					}

					@Override
					public void setReceptionArea(int x, int y, int width,
							int height) {
						for (int i = x; i < x + width; i++) {
							for (int j = y; j < y + height; j++) {
								grid[i][j].setState(State.DESTINATION);
							}
						}
					}

					@Override
					public void setObstacle(int x, int width) {
						for (int i = x; i < x + width; i++) {
							for (int j = 0; j < EnvironmentUpdater.GRID_HEIGHT; j++) {
								grid[i][j].setState(State.OBSTACLE);
							}
						}
						obstacleX = x;
						obstacleWidth = width;
					}

					@Override
					public void addCorridor(int y) {
						for (int i = 0; i < EnvironmentUpdater.GRID_WIDTH; i++) {
							if (grid[i][y].getState() == State.OBSTACLE) {
								grid[i][y].setState(State.FREESPACE);
							}
						}
					}

					@Override
					public void removeCorridor(int y) {
						for (int i = obstacleX; i < obstacleX + obstacleWidth; i++) {
							if (grid[i][y].getState() == State.OBSTACLE) {
								grid[i][y].setState(State.FREESPACE);
							}
						}
					}

				};
			}

			@Override
			protected GridUpdater make_updateGrid() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected GridDataProvider make_dataProvider() {
				return new GridDataProvider() {

					@Override
					public State getState(int x, int y) {
						return grid[x][y].getState();
					}

					@Override
					public void setState(int x, int y, State state) {
						grid[x][y].setState(state);						
					}
				};
			}

		};
	}

}
