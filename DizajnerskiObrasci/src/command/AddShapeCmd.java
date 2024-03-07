package command;

import mvc.DrawingModel;
import mvc.Shape;

public class AddShapeCmd implements Command{

	private DrawingModel model;
	private Shape shape;
	
	public AddShapeCmd(DrawingModel model,Shape shape)
	{
		this.model=model;
		this.shape=shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		
	}

}
