package fr.sma.core;

public class RobotGui implements IRobotGui {
	
	private IRobot robot;

	@Override
	public boolean isAlive() {
		return robot.isAlive();
	}

}
