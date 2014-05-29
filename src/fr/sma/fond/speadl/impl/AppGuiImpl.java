package fr.sma.fond.speadl.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Fond.Gui;

import fr.sma.core.Cell;
import fr.sma.core.GridGui;
import fr.sma.fond.speadl.IGuiUpdate;
		
public class AppGuiImpl extends Gui {
	
	private JFrame window;
	private JPanel container;
	
	private GridGui gridGui;
	
	public AppGuiImpl(int width, int height) {
		window = new JFrame();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = new JPanel();
		gridGui = new GridGui(width, height);		
		container.add(gridGui, BorderLayout.CENTER);
		
		initComponents();
		
		window.setContentPane(container);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	protected IGuiUpdate make_guiUpdater() {
		return new IGuiUpdate() {

			@Override
			public void refresh() {
				List<Cell> cellList = requires().gridProvider().getGridContent();
				gridGui.updateGrid(cellList);
			}
			
		};
	}
	
	private void initComponents() {
		JPanel menuPanel = new JPanel(new GridLayout(20, 1, 0, 3));
		JButton testButton1 = new JButton("TODO");
		JButton testButton2 = new JButton("TODO");
		JButton testButton3 = new JButton("TODO");
		JButton testButton4 = new JButton("TODO");
		JButton testButton5 = new JButton("TODO");
		
		menuPanel.add(testButton1);
		menuPanel.add(testButton2);
		menuPanel.add(testButton3);
		menuPanel.add(testButton4);
		menuPanel.add(testButton5);
		
		container.add(menuPanel, BorderLayout.EAST);
	}
}
