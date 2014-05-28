package fr.sma.fond.speadl;

import java.util.List;

import fr.sma.core.Cell;
import fr.sma.core.Position;
import fr.sma.core.State;

public interface MemoryManager {

	public Position getNextPosition(List<Cell> cells, State goal, Position current);
}
