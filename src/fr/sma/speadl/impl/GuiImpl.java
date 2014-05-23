package fr.sma.speadl.impl;

import javax.swing.JFrame;

import fr.sma.core.EnvironmentGui;
import fr.sma.speadl.GuiHandler;
import DiezeFond.Environment;
import DiezeFond.Gui;

public class GuiImpl extends Gui {

	@Override
	protected GuiHandler make_guiHandler() {
		return new GuiHandler() {
			
			@Override
			public void init() {
				Environment.Component environment = (new EnvironmentImpl()).newComponent();
				environment.environmentHandler().setObstacle(25, 35);
				environment.environmentHandler().setExpeditionArea(0, 10, 10, 30);
				environment.environmentHandler().setReceptionArea(70, 10, 10, 30);
				environment.environmentHandler().addCorridor(0);
				environment.environmentHandler().addCorridor(49);
				EnvironmentGui grid = new EnvironmentGui(environment.environmentHandler());

				JFrame window = new JFrame();
				window.setSize(840, 560);
				window.setResizable(false);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				window.add(grid);
				window.setVisible(true);
			}
		};
	}

}
