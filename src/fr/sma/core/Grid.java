package fr.sma.core;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Grid extends JPanel {

	private static final long serialVersionUID = -3656866293410119738L;

	private List<Point> fillCells;
	private List<Point> storageCells;
	private List<Point> obstacleCells;

	public Grid() {
		fillCells = new ArrayList<Point>();
		storageCells = new ArrayList<Point>();
		obstacleCells = new ArrayList<Point>();
	}

	private void drawCells(Graphics g, List<Point> points, Color color) {
		for (Point point : points) {
			int cellX = 10 + (point.x * 10);
			int cellY = 10 + (point.y * 10);
			g.setColor(color);
			g.fillRect(cellX, cellY, 10, 10);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Render cells
		this.drawCells(g, fillCells, Color.RED);
		this.drawCells(g, storageCells, Color.GRAY);
		this.drawCells(g, obstacleCells, Color.DARK_GRAY);

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

	public void fillCell(int x, int y) {
		fillCells.add(new Point(x, y));
		repaint();
	}

	public void addExpeditionArea(int y, int width, int height) {
		int jMax = y + height;
		for (int i = 0; i < width; i++) {
			for (int j = y; j < jMax; j++) {
				storageCells.add(new Point(i, j));
			}
		}
		repaint();
	}

	public void addReceptionArea(int y, int width, int height) {
		int jMax = y + height;
		for (int i = 80 - width; i < 80; i++) {
			for (int j = y; j < jMax; j++) {
				storageCells.add(new Point(i, j));
			}
		}
		repaint();
	}

	public void addMainObstacle(int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				obstacleCells.add(new Point(i, j));
			}
		}
	}

}