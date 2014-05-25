package fr.sma.core;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.sma.speadl.EnvironmentUpdater;

public class GridGui extends JPanel {

	private static final long serialVersionUID = -3656866293410119738L;
	
	private Cell[][] content;

	private void drawCell(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		int cellX = 10 + (x * 10);
		int cellY = 10 + (y * 10);
		g.setColor(color);
		g.fillRect(cellX, cellY, 10, 10);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		System.out.println("############## PAINT ME ######################");

		for (int i = 0; i < EnvironmentUpdater.GRID_WIDTH; i++) {
			for (int j = 0; j < EnvironmentUpdater.GRID_HEIGHT; j++) {
				Color color;
				switch (content[i][j].getState()) {
				case EXPEDITION:
				case DESTINATION:
					color = Color.DARK_GRAY;
					break;
				case EMPTYROBOT:
					color = Color.GREEN;
					break;
				case OBSTACLE:
					color = Color.GRAY;
					break;
				case ROBOT:
					color = Color.ORANGE;
					break;
				case FREESPACE:
				default:
					color = Color.WHITE;
					break;
				}
				this.drawCell(g, i, j, color);
			}
		}

		// Render grid
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 800, 500);

		for (int i = 10; i <= 800; i += 10) {
			g.drawLine(i, 10, i, 510);
		}

		for (int i = 10; i <= 500; i += 10) {
			g.drawLine(10, i, 810, i);
		}
	}

	public Cell[][] getContent() {
		return content;
	}

	public void setContent(Cell[][] content) {
		this.content = content;
		repaint();
		System.out.println("grid gui set content " + content.length);
	}
}