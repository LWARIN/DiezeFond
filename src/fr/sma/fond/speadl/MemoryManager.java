package fr.sma.fond.speadl;

import java.util.List;

import fr.sma.fond.core.Cell;
import fr.sma.fond.core.Position;
import fr.sma.fond.core.State;

public interface MemoryManager {

	public Position getNextPosition(List<Cell> cells, State goal, Position current);
}
