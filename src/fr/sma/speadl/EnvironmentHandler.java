package fr.sma.speadl;

import fr.sma.core.State;

public interface EnvironmentHandler {
	
	public final static int GRID_WIDTH = 100;
	public final static int GRID_HEIGHT = 50;

	public void setExpeditionArea(int x, int y, int width, int height);
	
	public void setReceptionArea(int x, int y, int width, int height);
	
	public void setObstacle(int x, int width);
	
	public void addCorridor(int y);
	
	public void removeCorridor(int y);
	
	public State getState(int x, int y);
	
	public void setState(int x, int y, State state);
}
