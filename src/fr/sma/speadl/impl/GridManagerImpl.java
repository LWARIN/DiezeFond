package fr.sma.speadl.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.core.State;
import fr.sma.speadl.EnvironmentUpdater;
import fr.sma.speadl.GridDataProvider;
import fr.sma.speadl.GridUpdater;
import DiezeFond.GridManager;

public class GridManagerImpl extends GridManager {
	
	private final static Logger LOGGER = Logger.getLogger(GridManagerImpl.class.getName());

	private Cell[][] grid;
	private int obstacleX;
	private int obstacleWidth;
	
	public GridManagerImpl() {
		grid = new Cell[EnvironmentUpdater.GRID_WIDTH][EnvironmentUpdater.GRID_HEIGHT];
		for (int i = 0; i < EnvironmentUpdater.GRID_WIDTH; ++i) {
			for (int j = 0; j < EnvironmentUpdater.GRID_HEIGHT; ++j) {
				grid[i][j] = new Cell(new Position(i, j));
			}
		}
	}
	
	@Override
	protected EnvironmentUpdater make_updateEnvironment() {
		return new EnvironmentUpdater() {

			@Override
			public void setExpeditionArea(int x, int y, int width,
					int height) {
				LOGGER.log(Level.INFO, "Creating expedition area: (" + x + ", " + y + "); width: " + width + "; height: " + height);
				for (int i = x; i < x + width; i++) {
					for (int j = y; j < y + height; j++) {
						grid[i][j].setState(State.EXPEDITION);
					}
				}
			}

			@Override
			public void setReceptionArea(int x, int y, int width,
					int height) {
				LOGGER.log(Level.INFO, "Creating reception area: (" + x + ", " + y + "); width: " + width + "; height: " + height);
				for (int i = x; i < x + width; i++) {
					for (int j = y; j < y + height; j++) {
						grid[i][j].setState(State.DESTINATION);
					}
				}
			}

			@Override
			public void setObstacle(int x, int width) {
				LOGGER.log(Level.INFO, "Creating obstacle on column: " + x +  "; width: " + width);
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
				LOGGER.log(Level.INFO, "Creating corridor on line: " + y);
				for (int i = 0; i < EnvironmentUpdater.GRID_WIDTH; i++) {
					if (grid[i][y].getState() == State.OBSTACLE) {
						grid[i][y].setState(State.FREESPACE);
					}
				}
			}

			@Override
			public void removeCorridor(int y) {
				LOGGER.log(Level.INFO, "Removing corridor on line: " + y);
				for (int i = obstacleX; i < obstacleX + obstacleWidth; i++) {
					if (grid[i][y].getState() == State.OBSTACLE) {
						grid[i][y].setState(State.FREESPACE);
					}
				}
			}
			
		};
	}

	@Override
	protected GridDataProvider make_dataProvider() {
		return new GridDataProvider() {

			@Override
			public Cell[][] getGridContent() {
				return grid;
			}			
		};
	}
	
	@Override
	protected GridUpdater make_updateGrid() {
		return new GridUpdater() {

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

}
