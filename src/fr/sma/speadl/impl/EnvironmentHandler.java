package fr.sma.speadl.impl;

public interface EnvironmentHandler {

	public void setExpeditionArea(int x, int y, int width, int height);
	
	public void setReceptionArea(int x, int y, int width, int height);
	
	public void setObstacle(int x, int width);
	
	public void addCorridor(int y);
	
	public void removeCorridor(int y);
}
