package fr.sma.speadl.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DiezeFond.AppGUI;
import fr.sma.speadl.GuiConnector;

public class AppGuiImpl extends AppGUI {

	private JFrame window;
	private JPanel container;

	public AppGuiImpl() {
		window = new JFrame();
		container = new JPanel();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(container);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	protected GuiConnector make_guiLink() {
		return new GuiConnector() {

			@Override
			public void setEnvironmentGui(JPanel content) {
				System.out.println("APP GUI SET ENVIRONMENT GUI");
				container.removeAll();
				System.out.println(content.getSize());
				container.add(content);
				window.pack();
				window.setLocationRelativeTo(null);
			}
		};
	}
}
