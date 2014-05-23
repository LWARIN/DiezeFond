package fr.sma.core;

public class Cell {
	
	private State state = State.FREESPACE;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
	
}
