package fr.sma.speadl.impl;

import fr.sma.core.Cell;
import fr.sma.core.GridGui;
import fr.sma.speadl.EnvironmentRenderer;
import DiezeFond.EnvironmentGUI;

public class EnvironmentGuiImpl extends EnvironmentGUI {

	private GridGui gridGui;
	
	public EnvironmentGuiImpl() {
		gridGui = new GridGui();
	}
	
	@Override
	protected EnvironmentRenderer make_renderEnvironment() {
		
		
		return new EnvironmentRenderer() {

			@Override
			public void refresh() {
				Cell[][] gridContent = requires().dataProvider().getGridContent();
				gridGui.setContent(gridContent);
				gridGui.repaint();
			}
		};
	}

}
