package fr.sma.fond;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import Fond.App;
import fr.sma.fond.speadl.impl.AppImpl;

public class Main {

	public static void main(String[] args) {
		int nbRobots = 0;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("diezefond.properties");
			prop.load(input);
			nbRobots = Integer.parseInt(prop.getProperty("nbRobots"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException nex) {
			nbRobots = 50;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		App.Component app = (new AppImpl()).newComponent();
		app.initManager().setNbRobots(nbRobots);
		app.log().addObserver(app.guiLog());
		app.initManager().addCorridor(0);
		app.initManager().addCorridor(49);
	}
}