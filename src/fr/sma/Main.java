package fr.sma;

import fr.sma.speadl.impl.EnvironmentImpl;
import fr.sma.speadl.impl.GuiImpl;
import fr.sma.speadl.impl.RobotFactoryImpl;
import DiezeFond.Environment;
import DiezeFond.Gui;
import DiezeFond.RobotFactory;

public class Main {

	public static void main(String[] args) {
		Gui.Component guy = (new GuiImpl()).newComponent();
		guy.guiHandler().init();
		
		RobotFactory.Component robotFactory = (new RobotFactoryImpl()).newComponent();
		robotFactory.robotHandler().initRobots(3);
	}
}