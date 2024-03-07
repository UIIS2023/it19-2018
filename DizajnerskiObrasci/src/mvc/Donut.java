package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class Donut extends Circle {

	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}
	

		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
			
			path.append(new Ellipse2D.Double(getCenter().getX() - getRadius(), this.getCenter().getY() - getRadius(), getRadius()*2, getRadius()*2), false);
			path.append(new Ellipse2D.Double(getCenter().getX() - getInnerRadius(), this.getCenter().getY() - getInnerRadius(), getInnerRadius()*2, getInnerRadius()*2), false);
			
			g2d.setColor(getInnerColor());
			g2d.fill(path);
			
			g2d.setColor(getColor());
			g2d.drawOval(getCenter().getX() - getRadius(), this.getCenter().getY() - getRadius(), getRadius()*2, getRadius()*2);
			g2d.drawOval(getCenter().getX() - getInnerRadius(), this.getCenter().getY() - getInnerRadius(), getInnerRadius()*2, getInnerRadius()*2);
			
			if (isSelected()) {
				g2d.setColor(Color.BLUE);
				g2d.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
				g2d.drawRect(getCenter().getX() - 3 - getRadius(), getCenter().getY() - 3, 6, 6);
				g2d.drawRect(getCenter().getX() - 3 + getRadius(), getCenter().getY() - 3, 6, 6);
				g2d.drawRect(getCenter().getX() - 3, getCenter().getY() - 3 + getRadius() , 6, 6);
				g2d.drawRect(getCenter().getX() - 3, getCenter().getY() - 3 - getRadius(), 6, 6);
			}
		}

	

	/*public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - this.innerRadius, getCenter().getY() - this.innerRadius, this.innerRadius * 2,
				this.innerRadius * 2);

	}*/

	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) && this.getRadius() == d.getRadius()
					&& this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Donut clone()
	{
		Donut newDonut=new Donut();
		newDonut.setSelected(this.isSelected());
		newDonut.setColor(this.getColor());
		newDonut.setInnerColor(this.getInnerColor());
		newDonut.getCenter().setX(this.getCenter().getX());
		newDonut.getCenter().setY(this.getCenter().getY());
		try {
			newDonut.setInnerRadius(this.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			newDonut.setRadius(this.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDonut;
	}

	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}

	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}

	public int getInnerRadius() {
		return this.innerRadius;
	}

	public void setInnerRadius(int innerRadius) throws Exception {
		if (innerRadius > 0) {
			this.innerRadius = innerRadius;
		} else {
			throw new NumberFormatException("innerRadius has not to be a value greater then 0");
		}
	}

	public String toString() {
		return "donut "+ getCenter().getX() +" "+ getCenter().getY()+" "+ getRadius()+" "+ getInnerRadius()+ " "+getColor().getRGB() +" "+getInnerColor().getRGB();
	}

}