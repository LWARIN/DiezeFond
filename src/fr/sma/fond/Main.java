package fr.sma.fond;

import Fond.App;
import fr.sma.fond.speadl.impl.AppImpl;

public class Main {

	public static void main(String[] args) {
		
		App.Component app = (new AppImpl()).newComponent();
		app.log().addObserver(app.guiLog());
		app.initManager().addCorridor(0);
		app.initManager().addCorridor(49);
	}
}