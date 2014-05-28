package fr.sma.fond.speadl.impl;

import Fond.AppGUI;
import Fond.GUI;
import Fond.PlantGUI;

public class GuiImpl extends GUI {

	@Override
	protected AppGUI make_appGui() {
		return new AppGUI() {
			/*
			 * private JFrame window; private JPanel container;
			 * 
			 * public AppGuiImpl() { window = new JFrame(); container = new
			 * JPanel(); window.setResizable(false);
			 * window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 * window.setContentPane(container); window.pack();
			 * window.setLocationRelativeTo(null); window.setVisible(true); }
			 * 
			 * @Override protected GuiConnector make_guiLink() { return new
			 * GuiConnector() {
			 * 
			 * @Override public void setEnvironmentGui(JPanel content) {
			 * System.out.println("APP GUI SET ENVIRONMENT GUI");
			 * container.removeAll(); System.out.println(content.getSize());
			 * container.add(content); window.pack();
			 * window.setLocationRelativeTo(null); } }; }
			 */
		};
	}

	@Override
	protected PlantGUI make_plantGUI() {
		return new PlantGUI() {
			/*
			 * private GridGui gridGui;
			 * 
			 * public EnvironmentGuiImpl() { gridGui = new GridGui(); }
			 * 
			 * @Override protected EnvironmentRenderer make_renderEnvironment()
			 * {
			 * 
			 * return new EnvironmentRenderer() {
			 * 
			 * @Override public void refresh() { Cell[][] gridContent =
			 * requires().dataProvider().getGridContent();
			 * System.out.println("ENVIRONMENT GUI IMPL REFRESH : " +
			 * gridContent.length); gridGui.setContent(gridContent);
			 * gridGui.repaint();
			 * requires().guiLink().setEnvironmentGui(gridGui); } }; }
			 */
		};
	}

}
