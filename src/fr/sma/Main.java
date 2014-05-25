package fr.sma;

import fr.sma.speadl.impl.EcoEnvironmentImpl;
import DiezeFond.EcoEnvironment;

public class Main {

	public static void main(String[] args) {
	
		EcoEnvironment.Component ecosystem = (new EcoEnvironmentImpl()).newComponent();
		ecosystem.gridHandler().setObstacle(25, 35);
		ecosystem.gridHandler().setExpeditionArea(0, 10, 10, 30);
		ecosystem.gridHandler().setReceptionArea(70, 10, 10, 30);
		ecosystem.gridHandler().addCorridor(0);
		ecosystem.gridHandler().addCorridor(49);
	}
}