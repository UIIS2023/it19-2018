package command;

import java.util.ArrayList;

import mvc.Point;
import mvc.Shape;

public class UpdatePointCmd implements Command {

	private Point oldPoint;
	private Point newPoint;
	private Point originalPoint=new Point();

	
	public UpdatePointCmd(Point p1,Point p2 )
	{
		oldPoint=p1;
		newPoint=p2;
	
	}
	
	@Override
	public void execute() {
		
		originalPoint=oldPoint.clone();
		oldPoint.setX(newPoint.getX());
		oldPoint.setY(newPoint.getY());
		oldPoint.setColor(newPoint.getColor());
	}

	@Override
	public void unexecute() {
		oldPoint.setX(originalPoint.getX());
		oldPoint.setY(originalPoint.getY());
		oldPoint.setColor(originalPoint.getColor());
	}
	

}
