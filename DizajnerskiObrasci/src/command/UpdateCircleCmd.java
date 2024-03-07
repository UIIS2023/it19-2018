package command;

import java.util.ArrayList;

import mvc.Circle;
import mvc.Shape;

public class UpdateCircleCmd implements Command{
	
	private Circle oldCircle;
	private Circle newCircle;
	private Circle originalCircle=new Circle();
	
	
	public UpdateCircleCmd(Circle c1, Circle c2)
	{
		oldCircle=c1;
		newCircle=c2;
		
	}

	@Override
	public void execute() {
		
		originalCircle=oldCircle.clone();
		
		oldCircle.setColor(newCircle.getColor());
		oldCircle.setInnerColor(newCircle.getInnerColor());
		oldCircle.getCenter().setX(newCircle.getCenter().getX());
		oldCircle.getCenter().setY(newCircle.getCenter().getY());
		try {
			oldCircle.setRadius(newCircle.getRadius());
		} catch (Exception e) {
			System.out.println("Stanje nije validno");
		}
		
	}

	@Override
	public void unexecute() {
		oldCircle.setColor(originalCircle.getColor());
		oldCircle.setInnerColor(originalCircle.getInnerColor());
		oldCircle.getCenter().setX(originalCircle.getCenter().getX());
		oldCircle.getCenter().setY(originalCircle.getCenter().getY());
		try {
			oldCircle.setRadius(originalCircle.getRadius());
		} catch (Exception e) {
			System.out.println("Stanje nije validno");
		}
		
		
	}

}
