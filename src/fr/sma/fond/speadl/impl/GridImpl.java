package fr.sma.fond.speadl.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.sma.fond.core.Cell;
import fr.sma.fond.core.Position;
import fr.sma.fond.core.State;
import fr.sma.fond.speadl.GridManager;
import fr.sma.fond.speadl.GridProvider;
import Fond.Grid;

public class GridImpl extends Grid {

	private Cell[][] grid;

	@Override
	protected void start() {
		super.start();

		grid = new Cell[GridManager.GRID_WIDTH][GridManager.GRID_HEIGHT];
		for (int i = 0; i < GridManager.GRID_WIDTH; ++i) {
			for (int j = 0; j < GridManager.GRID_HEIGHT; ++j) {
				grid[i][j] = new Cell(new Position(i, j));
			}
		}

		requires().log().info("GridImpl", "Creating expedition area");
		for (int i = 0; i < 5; i++) {
			for (int j = 20; j < 20 + 10; j++) {
				grid[i][j].setState(State.EXPEDITION);
			}
		}

		requires().log().info("GridImpl", "Creating reception area");
		for (int i = 95; i < GridManager.GRID_WIDTH; i++) {
			for (int j = 20; j < 20 + 10; j++) {
				grid[i][j].setState(State.DESTINATION);
			}
		}

		requires().log().info("GridImpl", "Creating obstacle");
		for (int i = 35; i < 35 + 30; i++) {
			for (int j = 0; j < GridManager.GRID_HEIGHT; j++) {
				grid[i][j].setState(State.OBSTACLE);
			}
		}

	}

	@Override
	protected GridManager make_gridManager() {
		return new GridManager() {

			@Override
			public void addCorridor(int y) {
				requires().log().info("GridImpl", "Creating corridor on line: " + y);
				for (int i = 35; i < 35 + 30; i++) {
					if (grid[i][y].getState() == State.OBSTACLE) {
						grid[i][y].setState(State.FREESPACE);
					}
				}
			}

			@Override
			public void removeCorridor(int y) {
				requires().log().info("GridImpl", "Removing corridor on line: " + y);
				for (int i = 35; i < 35 + 30; i++) {
					if (grid[i][y].getState() == State.FREESPACE) {
						grid[i][y].setState(State.OBSTACLE);
					}
				}
			}

		};
	}

	@Override
	protected GridProvider make_gridProvider() {
		return new GridProvider() {

			@Override
			public State getState(int x, int y) {
				return grid[x][y].getState();
			}

			@Override
			public void setState(int x, int y, State state) {
				grid[x][y].setState(state);
			}

			@Override
			public List<Cell> getNeighbors(Position current) {
				int x = current.getX();
				int y = current.getY();

				int xMin = (x - 3) <= 0 ? 0 : (x - 3);
				int xMax = (x + 3) > GridManager.GRID_WIDTH ? GridManager.GRID_WIDTH : (x + 3);

				int yMin = (y - 3) <= 0 ? 0 : (y - 3);
				int yMax = (y + 3) > GridManager.GRID_HEIGHT ? GridManager.GRID_HEIGHT : (y + 3);

				List<Cell> neighbors = new ArrayList<Cell>();
				for (int i = xMin; i < xMax; i++) {
					for (int j = yMin; j < yMax; j++) {
						Cell cell = new Cell(new Position(i, j));
						cell.setState(getState(i, j));
						neighbors.add(cell);
					}
				}

				Collections.shuffle(neighbors);

				return neighbors;
			}

			@Override
			public List<Cell> getGridContent() {
				List<Cell> cellList = new ArrayList<>();
				for (int i = 0; i < GridManager.GRID_WIDTH; ++i) {
					for (int j = 0; j < GridManager.GRID_HEIGHT; ++j) {
						cellList.add(grid[i][j]);
					}
				}
				return cellList;
			}

			@Override
			public Position getRandomFreeCell() {
				Random rand = new Random();
				int x = 0, y = 0;
				State state = null;
				while (state != State.FREESPACE) {
					x = rand.nextInt(GridManager.GRID_WIDTH);
					y = rand.nextInt(GridManager.GRID_HEIGHT);
					state = grid[x][y].getState();
				}

				return grid[x][y].getPosition();
			}

		};
	}
}
