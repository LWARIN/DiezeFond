package fr.sma.speadl.impl;

import fr.sma.core.Robot;
import fr.sma.speadl.RobotHandler;
import DiezeFond.RobotFactory;

public class RobotFactoryImpl extends RobotFactory {

	@Override
	protected RobotHandler make_robotHandler() {
		return new RobotHandler() {

			@Override
			public void initRobots(int nbRobots) {
				for (int i = 0; i < nbRobots; ++i) {					
					try {
						new Thread(new Robot()).start();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

}
