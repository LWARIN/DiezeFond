package fr.sma;

import fr.sma.speadl.impl.EcoEnvironmentImpl;
import DiezeFond.EcoEnvironment;

public class Main {

	public static void main(String[] args) {
	
		EcoEnvironment.Component ecosystem = (new EcoEnvironmentImpl()).newComponent();
		//	robotFactory.robotHandler().initRobots(3);
		
		
		/*
		 * 
		 * environment.environmentHandler().setObstacle(25, 35);
		 * environment.environmentHandler().setExpeditionArea(0, 10, 10, 30);
		 * environment.environmentHandler().setReceptionArea(70, 10, 10, 30);
		 * environment.environmentHandler().addCorridor(0);
		 * environment.environmentHandler().addCorridor(49); EnvironmentGui grid =
		 * new EnvironmentGui(environment.environmentHandler());
		 * 
		 * JFrame window = new JFrame(); window.setSize(840, 560);
		 * window.setResizable(false);
		 * window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * 
		 * window.add(grid); window.setVisible(true);
		 */
	}
}