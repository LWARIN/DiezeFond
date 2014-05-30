package fr.sma.fond.speadl;

import java.util.List;

import fr.sma.fond.core.Cell;
import fr.sma.fond.core.Position;
import fr.sma.fond.core.State;

public interface GridProvider {
	
	public Position getRandomFreeCell();
	
	public State getState(int x, int y);
	
	public void setState(int x, int y, State state);
	
	public List<Cell> getGridContent();
	
	public List<Cell> getNeighbors(Position current);
}

