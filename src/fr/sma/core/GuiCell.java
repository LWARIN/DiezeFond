package fr.sma.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GuiCell extends JPanel {

	private static final long serialVersionUID = 4995961574164475976L;

	private final static Color FREESPACE_COLOR = new Color(0xFFFFDE);

	private State state = State.FREESPACE;
	private Color currentColor;

	public GuiCell() {
		setPreferredSize(new Dimension(14, 14));
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}

	@Override
	protected void paintComponent(Graphics g) {
		switch (state) {
		case EXPEDITION:
		case DESTINATION:
			currentColor = Color.DARK_GRAY;
			break;
		case EMPTYROBOT:
			currentColor = Color.GREEN;
			break;
		case OBSTACLE:
			currentColor = Color.GRAY;
			break;
		case ROBOT:
			currentColor = Color.ORANGE;
			break;
		case FREESPACE:
			currentColor = FREESPACE_COLOR;
			break;
		}
		g.setColor(currentColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void setState(State state) {
		this.state = state;
	}
}
