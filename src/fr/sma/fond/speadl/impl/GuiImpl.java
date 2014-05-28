package fr.sma.fond.speadl.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiImpl {
	
	private JFrame window;
	private JPanel container;
	
	public GuiImpl() {
		window = new JFrame();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(container);
		container = new JPanel();
	}
}
