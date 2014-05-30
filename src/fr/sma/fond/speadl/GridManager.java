package fr.sma.fond.speadl;

public interface GridManager {
	public final static int GRID_WIDTH = 100;
	public final static int GRID_HEIGHT = 50;
	
	public void addCorridor(int y);
	
	public void removeCorridor(int y);
}
