package fr.sma.fond.speadl;

import java.util.List;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.core.State;

public interface GridProvider {
	
	public State getState(int x, int y);
	
	public void setState(int x, int y, State state);
	
	public List<Cell> getNeighbors(Position current);
}

