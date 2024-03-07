package command;

import java.util.Collections;

import mvc.DrawingModel;
import mvc.Shape;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int shapeIndex;
	
	public BringToBackCmd(DrawingModel model,Shape shape)
	{
		this.model=model;
		this.shape=shape;
	}
	
	@Override
	public void execute() {
		if(model.getShapes().contains(shape))
		{
			shapeIndex=model.getShapes().indexOf(shape);
			while(shapeIndex>0)
			{
				Collections.swap(model.getShapes(), shapeIndex, shapeIndex-1);
				shapeIndex--;
			}
		}	
	}

	@Override
	public void unexecute() {
		if(model.getShapes().contains(shape))
		{
			shapeIndex=model.getShapes().indexOf(shape);
			while(model.getShapes().size()-1!=shapeIndex)
			{
				Collections.swap(model.getShapes(), shapeIndex, shapeIndex+1);
				shapeIndex++;
			}
		}
		
	}

}
