package fr.sma.fond.speadl.impl;

import fr.sma.fond.speadl.InitManager;
import Fond.Clock;
import Fond.EcoRobot;
import Fond.Grid;
import Fond.Plant;

public class PlantImpl extends Plant {

	@Override
	protected InitManager make_initManager() {
		return new InitManager() {

			@Override
			public void setNbRobots(int nbRobots) {
				// TODO Link with robots
			}

			@Override
			public void setObstacle(int x, int width) {
				parts().grid().gridManager().setObstacle(x, width);
			}

			@Override
			public void setExpeditionArea(int x, int y, int width, int height) {
				parts().grid().gridManager().setExpeditionArea(x, y, width, height);
			}

			@Override
			public void setReceptionArea(int x, int y, int width, int height) {
				parts().grid().gridManager().setExpeditionArea(x, y, width, height);
			}

			@Override
			public void addCorridor(int y) {
				parts().grid().gridManager().addCorridor(y);
			}
		};
	}

	@Override
	protected Grid make_grid() {
		return new GridImpl();
	}

	@Override
	protected EcoRobot make_robots() {
		return new EcoRobotImpl();
	}

	@Override
	protected Clock make_clock() {
		return new ClockImpl();
	}

}
