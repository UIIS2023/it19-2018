package command;


import mvc.Rectangle;

public class UpdateRectangleCmd implements Command {

	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	private Rectangle originalRectangle=new Rectangle();
	
	
	public UpdateRectangleCmd(Rectangle r1,Rectangle r2)
	{
		oldRectangle=r1;
		newRectangle=r2;
	}
	
	@Override
	public void execute() {
		
		originalRectangle=oldRectangle.clone();
	
		oldRectangle.setColor(newRectangle.getColor());
		oldRectangle.setHeight(newRectangle.getHeight());
		oldRectangle.setWidth(newRectangle.getWidth());
		oldRectangle.getUpperLeftPoint().setX(newRectangle.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(newRectangle.getUpperLeftPoint().getY());
		
	}

	@Override
	public void unexecute() {
		
		oldRectangle.setColor(originalRectangle.getColor());
		oldRectangle.setHeight(originalRectangle.getHeight());
		oldRectangle.setWidth(originalRectangle.getWidth());
		oldRectangle.getUpperLeftPoint().setX(originalRectangle.getUpperLeftPoint().getX());
		oldRectangle.getUpperLeftPoint().setY(originalRectangle.getUpperLeftPoint().getY());
	
	}
	
	

}
