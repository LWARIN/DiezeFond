package fr.sma.fond.speadl;

public interface InitManager {
	public void setNbRobots(int nbRobots);
	
	public void setObstacle(int x, int width);
	
	public void setExpeditionArea(int x, int y, int width, int height);
	
	public void setReceptionArea(int x, int y, int width, int height);
	
	public void addCorridor(int y);
}
