package fr.sma.core;

public class Cell {

	private State state;
	private Position position;
	private boolean updated;

	public Cell(Position position) {
		state = State.FREESPACE;
		this.position = position;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		setUpdated(true);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public String toString() {
		return "Cell: {State: " + state + ", " + position + "}";
	}
}
