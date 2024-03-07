package command;

import java.util.Collections;

import mvc.DrawingModel;
import mvc.Shape;

public class ToFrontCmd implements Command{
	
	private DrawingModel model;
	private Shape shape;
	private int shapeIndex;
	
	public ToFrontCmd(DrawingModel model,Shape shape)
	{
		this.model=model;
		this.shape=shape;
	}

	@Override
	public void execute() {
		if(model.getShapes().contains(shape))
		{
			shapeIndex=model.getShapes().indexOf(shape);
			if(shapeIndex<model.getShapes().size()-1)
			{
				Collections.swap(model.getShapes(), shapeIndex, shapeIndex+1);
			}
		}
	}

	@Override
	public void unexecute() {
		if(model.getShapes().contains(shape))
		{
			shapeIndex=model.getShapes().indexOf(shape);
			if(shapeIndex>0)
			{
				Collections.swap(model.getShapes(), shapeIndex, shapeIndex-1);
			}
		}
		
	}
	
}
