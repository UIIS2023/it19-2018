package strategy;

import mvc.DrawingFrame;

public class SaveContext {

	private Save save;
	
	public SaveContext(Save save)
	{
		this.save=save;
	}

	public void executeSave(DrawingFrame frame)
	{
		save.execute(frame);
	}
	
	public Save getSave() {
		return save;
	}

	public void setSave(Save save) {
		this.save = save;
	}
	
	

}
