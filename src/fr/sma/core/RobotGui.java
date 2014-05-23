package fr.sma.core;

public class RobotGui implements IRobotGui {
	
	private IRobot robot;
	
	public RobotGui(IRobot robot) {
		this.robot = robot;
	}

	@Override
	public boolean isAlive() {
		return robot.isAlive();
	}

}
