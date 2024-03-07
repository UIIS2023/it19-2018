package adapter;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;
import mvc.Point;
import mvc.Rectangle;
import mvc.Shape;

public class HexagonAdapter extends Shape {

	private Hexagon hexagon=new Hexagon(0,0,0);
	
	public HexagonAdapter()
	{
		
	}
	public HexagonAdapter(Point p)
	{
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());	
	}
	public HexagonAdapter(Point p,int r)
	{
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());	
		hexagon.setR(r);
	}
	
	public HexagonAdapter(Point p,int r, Color color1,Color color2)
	{
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());	
		hexagon.setR(r);
		hexagon.setAreaColor(color1);
		hexagon.setBorderColor(color2);
	}
	
	public HexagonAdapter(Point p,int r, Color color1,Color color2,boolean selected)
	{
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());	
		hexagon.setR(r);
		hexagon.setAreaColor(color1);
		hexagon.setBorderColor(color2);
		hexagon.setSelected(selected);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		hexagon.setX(hexagon.getX()+byX);
		hexagon.setY(hexagon.getY()+byY);
	}

	@Override
	public int compareTo(Object arg0) {
		
		if (arg0 instanceof HexagonAdapter) {
			return (this.hexagon.getR() - ((HexagonAdapter) arg0).getHexagon().getR());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		
		return hexagon.doesContain(x, y);
		
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter prosledjeni = (HexagonAdapter) obj;
			if (this.getHexagon().getX()==prosledjeni.getHexagon().getX() && this.getHexagon().getY()==prosledjeni.getHexagon().getY()
					&& this.getHexagon().getR() == prosledjeni.getHexagon().getR())
					{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		
	}

	@Override
	public Shape clone() {
		HexagonAdapter newHex=new HexagonAdapter();
		
		newHex.hexagon.setX(this.hexagon.getX());
		newHex.hexagon.setY(this.hexagon.getY());
		newHex.hexagon.setR(this.hexagon.getR());
		newHex.hexagon.setSelected(this.hexagon.isSelected());
		newHex.hexagon.setAreaColor(this.hexagon.getAreaColor());
		newHex.hexagon.setBorderColor(this.hexagon.getBorderColor());
		
		return newHex;
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public String toString() {
		return "hexagon " + getHexagon().getX() + " " + getHexagon().getY()+" "+getHexagon().getR()+" "+ getHexagon().getAreaColor().getRGB() +" "
	+ getHexagon().getBorderColor().getRGB();
	}

}
