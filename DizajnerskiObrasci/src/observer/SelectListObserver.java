package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class SelectListObserver implements PropertyChangeListener {

	private int listSize;
	private DrawingFrame frame;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("Size"))
		{
			listSize=(int)evt.getNewValue();
			this.changeButton();
		}
		
	}
	
	public SelectListObserver(DrawingFrame frame)
	{
		this.frame=frame;
	}
	
	public void changeButton() 
	{
		if(listSize==1)
		{
			frame.getTglbtnDelete().setEnabled(true);
			frame.getTglbtnModify().setEnabled(true);
			frame.getTglbtnBringToFront().setEnabled(true);
			frame.getTglbtnBringToBack().setEnabled(true);
			frame.getTglbtnToBack().setEnabled(true);
			frame.getTglbtnToFront().setEnabled(true);
		}
		else if(listSize>1)
		{
			frame.getTglbtnDelete().setEnabled(true);
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnToBack().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
		}
		else
		{
			frame.getTglbtnDelete().setEnabled(false);
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnToBack().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
		}
	}
	
	

}
