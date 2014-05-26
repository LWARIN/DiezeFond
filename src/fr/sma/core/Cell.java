package fr.sma.core;

public class Cell {

	private State state;
	private Position position;

	public Cell(Position position) {
		state = State.FREESPACE;
		this.position = position;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
