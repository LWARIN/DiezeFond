package fr.sma.speadl.impl;

import javax.swing.JFrame;

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
}
