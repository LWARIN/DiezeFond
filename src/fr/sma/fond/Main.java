package fr.sma.fond;

import fr.sma.fond.speadl.impl.PlantImpl;
import Fond.Plant;

public class Main {

	public static void main(String[] args) {
	
		Plant.Component plant = (new PlantImpl()).newComponent();
		plant.initManager().setObstacle(25, 35);
		plant.initManager().setExpeditionArea(0, 10, 10, 30);
		plant.initManager().setReceptionArea(70, 10, 10, 30);
		plant.initManager().addCorridor(0);
		plant.initManager().addCorridor(49);
	}
}