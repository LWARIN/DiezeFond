package fr.sma.speadl.impl;

import javax.swing.JFrame;

import fr.sma.speadl.EnvironmentRenderer;
import DiezeFond.AppGUI;
import DiezeFond.Ecosystem.GUI;
import DiezeFond.EnvironmentGUI;

public class GuiImpl extends GUI {


	private JFrame window;

	public GuiImpl() {
		window = new JFrame();
		window.setSize(840, 560);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	protected EnvironmentGUI make_environmentGui() {
		
		return new EnvironmentGUI() {

			@Override
			protected EnvironmentRenderer make_renderEnvironment() {
				return new EnvironmentRenderer() {

					@Override
					public void refresh() {

					}
				};
			}
		};
	}

	@Override
	protected AppGUI make_appGui() {
		return new AppGUI() {

		};
	}

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
