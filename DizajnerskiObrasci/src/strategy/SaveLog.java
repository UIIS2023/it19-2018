package strategy;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import mvc.DrawingFrame;

public class SaveLog  implements Save{

	@Override
	public void execute(DrawingFrame frame) {
		// create an object of JFileChooser class 
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
        		   FileWriter writer = new FileWriter(file);
        		   String log="";
        		   for(int i=0;i<frame.getDlm().getSize();i++)
        		   {
        			   log+=frame.getDlm().getElementAt(i) + "\n";
        		   }
        		   writer.write(log);
        		   writer.close();
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
