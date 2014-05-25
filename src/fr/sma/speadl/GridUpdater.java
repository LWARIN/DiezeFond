package fr.sma.speadl;

import fr.sma.core.State;

public interface GridUpdater {
	
	public State getState(int x, int y);
	public void setState(int x, int y, State state);
}
