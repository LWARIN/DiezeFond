package fr.sma.fond.speadl.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import Fond.Gui;
import fr.sma.fond.core.Cell;
import fr.sma.fond.core.GridGui;
import fr.sma.fond.core.Level;
import fr.sma.fond.speadl.IGuiLogger;
import fr.sma.fond.speadl.IGuiUpdate;
		
public class AppGuiImpl extends Gui {
	
	private JFrame window;
	private JPanel container;
	
	private JTextArea logArea;
	
	private GridGui gridGui;
	
	public AppGuiImpl(int width, int height) {
		window = new JFrame();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = new JPanel(new BorderLayout(0, 10));
		gridGui = new GridGui(width, height);		
		
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
		
		logArea = new JTextArea();
		((DefaultCaret) logArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		logArea.setEditable(false);
		JScrollPane logPanel = new JScrollPane(logArea);
		logPanel.setPreferredSize(new Dimension(0, 100));
		
		
		container.add(gridGui, BorderLayout.CENTER);
		container.add(menuPanel, BorderLayout.EAST);
		container.add(logPanel, BorderLayout.SOUTH);
	}

	@Override
	protected IGuiLogger make_guiLogger() {
		return new IGuiLogger() {

			@Override
			public void update(Level level, String message) {
				switch (level) {
				case INFO:
					logInfo(message);
					break;
				case WARNING:
					logWarning(message);
					break;
				case ERROR:
					logError(message);
					break;
				}
			}

			@Override
			public void logInfo(String message) {
				logArea.append("\n" + "INFO: " + message);
			}

			@Override
			public void logWarning(String message) {
				logArea.append("\n" + "WARNING: " + message);
			}

			@Override
			public void logError(String message) {
				logArea.append("\n" + "ERROR: " + message);
			}			
		};
	}
}
