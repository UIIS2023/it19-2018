package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import mvc.Shape;

import mvc.DrawingFrame;

public class SaveDrawing implements Save {

	@Override
	public void execute(DrawingFrame frame) {
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 

        // invoke the showsSaveDialog function to show the save dialog 
        int r = j.showSaveDialog(null); 

        // if the user selects a file 
        if (r == JFileChooser.APPROVE_OPTION) 

        { 
           String path = j.getSelectedFile().getAbsolutePath();
           File file=new File(path);
           try{
        	   if(file.createNewFile())
        	   {
        		  
        		   FileOutputStream fout=new FileOutputStream(file);
        		   ObjectOutputStream out=new ObjectOutputStream(fout);  
        			  out.writeObject(frame.getView().getModel().getShapes()); 
        			  out.flush(); 
        		      out.close();
        	   }
           }
           catch(Exception e)
           {
        	   JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
   					JOptionPane.INFORMATION_MESSAGE);	 
           }
        } 
        // if the user cancelled the operation 
        else
        	JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
					JOptionPane.INFORMATION_MESSAGE);	
	}

}
