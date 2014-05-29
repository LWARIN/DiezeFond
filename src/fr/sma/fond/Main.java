package fr.sma.fond;

import Fond.App;
import fr.sma.fond.speadl.impl.AppImpl;

public class Main {

	public static void main(String[] args) {
		
		App.Component app = (new AppImpl()).newComponent();
		
		app.initManager().setObstacle(35, 30);
		app.initManager().setExpeditionArea(0, 10, 10, 30);
		app.initManager().setReceptionArea(90, 10, 10, 30);
		app.initManager().addCorridor(0);
		app.initManager().addCorridor(49);
	}
}