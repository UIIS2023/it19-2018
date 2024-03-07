package command;

import java.util.ArrayList;
import java.util.Iterator;

import mvc.DrawingModel;
import mvc.Shape;

public class RemoveShapeCmd implements Command{

	private DrawingModel model;
	private ArrayList<Shape> shapes;
	private ArrayList<Shape> tempList=new ArrayList<Shape>();
	private Shape shape;
	
	public RemoveShapeCmd(DrawingModel model,ArrayList<Shape> shapes)
	{
		this.model=model;
		this.shapes=shapes;
		tempList.addAll(shapes);
	}
	
	@Override
	public void execute() {
		Iterator<Shape> it=tempList.iterator();
		while(it.hasNext())
		{
			shape=it.next();
			shape.setSelected(false);
			shapes.remove(shape);
			this.model.remove(shape);
		}
	}

	@Override
	public void unexecute() {
		
		shapes.addAll(tempList);
		Iterator<Shape> it=tempList.iterator();
		while(it.hasNext())
		{
			shape=it.next();
			shape.setSelected(true);
			model.add(shape);
		}
	}
}
