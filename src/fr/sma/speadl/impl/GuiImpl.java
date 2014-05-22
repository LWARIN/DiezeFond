package fr.sma.speadl.impl;

import javax.swing.JFrame;

import fr.sma.core.Grid;
import fr.sma.speadl.GuiHandler;
import DiezeFond.Gui;

public class GuiImpl extends Gui {

	@Override
	protected GuiHandler make_guiHandler() {
		return new GuiHandler() {
			
			@Override
			public void init() {
				Grid grid = new Grid();

				JFrame window = new JFrame();
				window.setSize(840, 560);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				grid.fillCell(0, 0);

				grid.addExpeditionArea(10, 10, 30);
				grid.addReceptionArea(10, 10, 30);
				grid.addMainObstacle(25, 1, 35, 48);

				window.add(grid);
				window.setVisible(true);
			}
		};
	}

}
