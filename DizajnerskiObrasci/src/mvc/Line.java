package mvc;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	private Point startPoint=new Point();
	private Point endPoint=new Point();

	public Line() {
	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		this.setSelected(selected);
	}

	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);

		this.setColor(color);
	}

	public Point middleOfLine() {
		int middleByX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
		int middleByY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}
	
	public Line clone()
	{
		Line newLine=new Line();
		newLine.setColor(this.getColor());
		newLine.setSelected(this.isSelected());
		newLine.getStartPoint().setX(this.getStartPoint().getX());
		newLine.getStartPoint().setY(this.getStartPoint().getY());
		newLine.getEndPoint().setX(this.getEndPoint().getX());
		newLine.getEndPoint().setY(this.getEndPoint().getY());
		return newLine;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.Length() - ((Line) o).Length());
		}
		return 0;
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(this.getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());

		if (isSelected()) {
			g.setColor(Color.blue);
			g.drawRect(this.startPoint.getX() - 3, this.startPoint.getY() - 3, 6, 6);
			g.drawRect(this.endPoint.getX() - 3, this.endPoint.getY() - 3, 6, 6);
			g.drawRect(middleOfLine().getX() - 3, middleOfLine().getY() - 3, 6, 6);
		}
	}

	public double Length() {
		return this.startPoint.distance(this.endPoint.getX(), this.endPoint.getY());
	}

	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line pomocna = (Line) obj;
			if (this.startPoint.equals(pomocna.getStartPoint()) && this.endPoint.equals(pomocna.getEndPoint())) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	public boolean contains(int x, int y) {
		if (this.startPoint.distance(x, y) + this.endPoint.distance(x, y) - this.Length() <= 0.25) {
			return true;
		} else {
			return false;
		}
	}
	

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public String toString() {
		return "line"+" "+ getStartPoint().getX() + " " + getStartPoint().getY() + " " + getEndPoint().getX() + " "
				+ getEndPoint().getY() + " " + getColor().getRGB();
	}

}
