package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SelectList {
	
	private int listSize;
	private PropertyChangeSupport propertyChangeSupport;
	
	public SelectList()
	{
		propertyChangeSupport=new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void setListSize(int listSize) {
		propertyChangeSupport.firePropertyChange("Size", this.listSize, listSize);
		this.listSize = listSize;
	}

}
