package fr.sma.speadl;

import java.util.List;

import fr.sma.core.Cell;
import fr.sma.core.Position;

public interface MoveHandler {
	
	public List<Cell> getNeighbors(Position current);
}
