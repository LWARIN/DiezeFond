package fr.sma.fond.speadl.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
	private int frequencyRate;
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
		frequencyRate = 500;

		JMenuBar menuBar = new JMenuBar();
		final JMenuItem playMenu = new JMenuItem("Play");
		playMenu.setEnabled(false);
		
		final JMenuItem pauseMenu = new JMenuItem("Pause");
		
		final JSpinner frequency = new JSpinner(new SpinnerNumberModel(500, 200, 2000, 50));
		frequency.setEnabled(false);

		frequency.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				frequencyRate = (int) frequency.getModel().getValue();
			}

		});

		playMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playMenu.setEnabled(false);
				pauseMenu.setEnabled(true);
				frequency.setEnabled(false);

				requires().clockManager().start(frequencyRate);
			}
		});

		pauseMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playMenu.setEnabled(true);
				pauseMenu.setEnabled(false);
				frequency.setEnabled(true);
				requires().clockManager().pause();
			}
		});

		menuBar.add(playMenu);
		menuBar.add(pauseMenu);
		menuBar.add(frequency);
		window.setJMenuBar(menuBar);

		logArea = new JTextArea();
		((DefaultCaret) logArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		logArea.setEditable(false);
		JScrollPane logPanel = new JScrollPane(logArea);
		logPanel.setPreferredSize(new Dimension(0, 100));

		container.add(gridGui, BorderLayout.CENTER);
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
