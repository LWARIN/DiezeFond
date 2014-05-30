package fr.sma.fond.speadl.impl;

import Fond.EcoRobot;
import Fond.Grid;
import Fond.Plant;
import fr.sma.fond.speadl.InitManager;

public class PlantImpl extends Plant {

	@Override
	protected InitManager make_initManager() {
		return new InitManager() {

			@Override
			public void setNbRobots(int nbRobots) {
				// TODO Link with robots
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

}
