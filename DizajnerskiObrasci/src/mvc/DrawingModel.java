package mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape s) {
		shapes.add(s);
	}

	public void remove(Shape s) {
			shapes.remove(s);
		}
		

	public Shape get(int index) {
		return shapes.get(index);
	}

}