package fr.sma;

import fr.sma.speadl.impl.EcosystemImpl;
import DiezeFond.Ecosystem;

public class Main {

	public static void main(String[] args) {
	
		Ecosystem.Component ecosystem = (new EcosystemImpl()).newComponent();
		//	robotFactory.robotHandler().initRobots(3);
	}
}