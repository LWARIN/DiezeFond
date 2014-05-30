package fr.sma.fond.core;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

public class GridGui extends JPanel {

	private static final long serialVersionUID = 5843720428162697514L;
	private GuiCell[] cellRepresentationTable;
	private int gridWidth;

	public GridGui(int width, int height) {
		super(new GridLayout(height, width));

		cellRepresentationTable = new GuiCell[(width * height)];
		for (int i = 0; i < cellRepresentationTable.length; ++i) {
			cellRepresentationTable[i] = new GuiCell();
			add(cellRepresentationTable[i]);
		}
		gridWidth = width;
	}

	public void updateGrid(List<Cell> cellList) {
		for (Cell cell : cellList) {
			if (cell.isUpdated()) {
				updateState(cell.getPosition(), cell.getState());
				cell.setUpdated(false);
			}
		}
	}

	private void updateState(Position position, State newState) {
		GuiCell tmp = cellRepresentationTable[position.getY() * gridWidth + position.getX()];
		tmp.setState(newState);
		tmp.repaint();
	}
}