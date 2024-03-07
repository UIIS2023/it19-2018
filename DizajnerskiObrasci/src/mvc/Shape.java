package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Moveable, Comparable, Serializable {

	
	private Color color;
	private boolean selected;

	public abstract boolean contains(int x, int y);

	public abstract void draw(Graphics g);
	
	public abstract Shape clone();
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
