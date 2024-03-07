package command;

import java.util.ArrayList;

import adapter.HexagonAdapter;
import mvc.Shape;


public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldHexagon;
	private HexagonAdapter newHexagon;
	private HexagonAdapter originalHexagon=new HexagonAdapter();
	
	
	public UpdateHexagonCmd(HexagonAdapter oldHex,HexagonAdapter newHex)
	{
		oldHexagon=oldHex;
		newHexagon=newHex;

	}
	
	@Override
	public void execute() {
		
		originalHexagon=(HexagonAdapter)oldHexagon.clone();
		
		oldHexagon.getHexagon().setX(newHexagon.getHexagon().getX());
		oldHexagon.getHexagon().setY(newHexagon.getHexagon().getY());
		oldHexagon.getHexagon().setR(newHexagon.getHexagon().getR());
		oldHexagon.getHexagon().setAreaColor(newHexagon.getHexagon().getAreaColor());
		oldHexagon.getHexagon().setBorderColor(newHexagon.getHexagon().getBorderColor());
	
		
	}

	@Override
	public void unexecute() {
		
		oldHexagon.getHexagon().setX(originalHexagon.getHexagon().getX());
		oldHexagon.getHexagon().setY(originalHexagon.getHexagon().getY());
		oldHexagon.getHexagon().setR(originalHexagon.getHexagon().getR());
		oldHexagon.getHexagon().setAreaColor(originalHexagon.getHexagon().getAreaColor());
		oldHexagon.getHexagon().setBorderColor(originalHexagon.getHexagon().getBorderColor());
		
	}
	

}
