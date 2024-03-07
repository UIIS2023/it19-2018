package command;

import java.util.ArrayList;

import mvc.Donut;
import mvc.Shape;

public class UpdateDonutCmd implements Command {

	private Donut oldDonut;
	private Donut newDonut;
	private Donut originalDonut=new Donut();
	
	
	public UpdateDonutCmd(Donut d1,Donut d2)
	{
		oldDonut=d1;
		newDonut=d2;
	
	}
	
	@Override
	public void execute() {
		
		originalDonut=	oldDonut.clone();
		
		oldDonut.setColor(newDonut.getColor());
		oldDonut.setInnerColor(newDonut.getInnerColor());
		oldDonut.getCenter().setX(newDonut.getCenter().getX());
		oldDonut.getCenter().setY(newDonut.getCenter().getY());
		try {
			oldDonut.setInnerRadius(newDonut.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			oldDonut.setRadius(newDonut.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void unexecute() {
		
		oldDonut.setColor(originalDonut.getColor());
		oldDonut.setInnerColor(originalDonut.getInnerColor());
		oldDonut.getCenter().setX(originalDonut.getCenter().getX());
		oldDonut.getCenter().setY(originalDonut.getCenter().getY());
		try {
			oldDonut.setInnerRadius(originalDonut.getInnerRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			oldDonut.setRadius(originalDonut.getRadius());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
