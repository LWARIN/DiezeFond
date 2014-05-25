package fr.sma.speadl.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.sma.speadl.GuiConnector;
import DiezeFond.AppGUI;

public class AppGuiImpl extends AppGUI {

	private JFrame window;

	public AppGuiImpl() {

		window = new JFrame();
		window.setSize(840, 560);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	protected GuiConnector make_guiLink() {
		return new GuiConnector() {

			@Override
			public void setEnvironmentGui(JPanel content) {
				window.add(content);
				window.pack();
			}
		};
	}
}
