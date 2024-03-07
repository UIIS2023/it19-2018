package command;

import java.util.ArrayList;

import mvc.Line;
import mvc.Shape;

public class UpdateLineCmd implements Command{
	
	private Line oldLine;
	private Line newLine;
	private Line originalLine=new Line();
	
	
	
	public UpdateLineCmd(Line l1,Line l2)
	{
		oldLine=l1;
		newLine=l2;
		
	}

	@Override
	public void execute() {
		
		originalLine=oldLine.clone();
		
		oldLine.setColor(newLine.getColor());
		oldLine.getStartPoint().setX(newLine.getStartPoint().getX());
		oldLine.getStartPoint().setY(newLine.getStartPoint().getY());
		oldLine.getEndPoint().setX(newLine.getEndPoint().getX());
		oldLine.getEndPoint().setY(newLine.getEndPoint().getY());
	}

	@Override
	public void unexecute() {
		
		oldLine.setColor(originalLine.getColor());
		oldLine.getStartPoint().setX(originalLine.getStartPoint().getX());
		oldLine.getStartPoint().setY(originalLine.getStartPoint().getY());
		oldLine.getEndPoint().setX(originalLine.getEndPoint().getX());
		oldLine.getEndPoint().setY(originalLine.getEndPoint().getY());
		
	}

}
