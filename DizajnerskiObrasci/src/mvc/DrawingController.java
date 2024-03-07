package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import observer.SelectList;
import observer.SelectListObserver;
import strategy.Save;
import strategy.SaveContext;
import strategy.SaveDrawing;
import strategy.SaveLog;

public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private Point startPoint;
	private Shape selected;
	private ArrayList<Shape> select=new ArrayList<Shape>();
	private SelectList selectList=new SelectList();
	private SelectListObserver selectListObserver;
	private Color innerColor;
	private Color outlineColor;
	private Stack<Command> stackRedo=new Stack<Command>();
	private Stack<Command> stackUndo=new Stack<Command>();
	private Stack<Command> stackRedoNext=new Stack<Command>();
	private Stack<Command> stackUndoNext=new Stack<Command>();
	private int positionInList=0;
	private Command command;
	private ArrayList<Shape> selectedNext=new ArrayList<Shape>();

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		selectListObserver=new SelectListObserver(frame);
		selectList.addPropertyChangeListener(selectListObserver);
	}

	public void mouseClicked(MouseEvent e) {
		Shape s = null;
		if (frame.getTglbtnSelect().isSelected()) {
			selected = null;
			Iterator<Shape> it = model.getShapes().iterator();
			while (it.hasNext()) {
				
				Shape shape = it.next();
				if (shape.contains(e.getX(), e.getY())) {
					selected = shape;
				}
			}
			if (selected != null)
			{
				if(selected.isSelected())
				{
					selected.setSelected(false);
					select.remove(selected);
					selectList.setListSize(select.size());
					frame.getDlm().addElement("Deselected "+selected.toString());
				}
				else
				{
					selected.setSelected(true);
					select.add(selected);
					selectList.setListSize(select.size());
					frame.getDlm().addElement("Selected "+selected.toString());
				}
			}
		}
		else if (frame.getTglbtnLine().isSelected()) {
			if (startPoint == null) {
				startPoint = new Point(e.getX(), e.getY(), false, Color.BLACK);
			} else {
				if(outlineColor==null)
				{
					s = new Line(startPoint, new Point(e.getX(), e.getY()), false, Color.BLACK);
				}
				else
				{
				s = new Line(startPoint, new Point(e.getX(), e.getY()), false,outlineColor);
				}
				startPoint = null;
			}
		} else if (frame.getTglbtnPoint().isSelected()) {
			if(outlineColor==null)
			{
				s = new Point(e.getX(), e.getY(), false, Color.BLACK);
			}
			else
			{
				s = new Point(e.getX(), e.getY(), false, outlineColor);
			}
		} else if (frame.getTglbtnCircle().isSelected()) {
			DlgCircle dlgCircle = new DlgCircle();

			dlgCircle.getTxtX().setText(Integer.toString(e.getX()));
			dlgCircle.getTxtY().setText(Integer.toString(e.getY()));

			dlgCircle.getTxtX().setEditable(false);
			dlgCircle.getTxtY().setEditable(false);
			
			if(innerColor!=null)
			{
				dlgCircle.getBtnInnerColor().setBackground(innerColor);
			}
			if(outlineColor!=null)
			{
				dlgCircle.getBtnOutlineColor().setBackground(outlineColor);
			}

			dlgCircle.setVisible(true);
			if (dlgCircle.isOk)
				s = dlgCircle.getCircle();
		} else if (frame.getTglbtnDonut().isSelected()) {
			DlgDonut dlgDonut = new DlgDonut();

			dlgDonut.getTxtX().setText(Integer.toString(e.getX()));
			dlgDonut.getTxtY().setText(Integer.toString(e.getY()));

			dlgDonut.getTxtX().setEditable(false);
			dlgDonut.getTxtY().setEditable(false);
			
			if(innerColor!=null)
			{
				dlgDonut.getBtnInnerColor().setBackground(innerColor);
			}
			if(outlineColor!=null)
			{
				dlgDonut.getBtnOutlineColor().setBackground(outlineColor);
			}

			dlgDonut.setVisible(true);
			if (dlgDonut.isOk)
				s = dlgDonut.getDonut();
		} else if (frame.getTglbtnRectangle().isSelected()) {
			DlgRectangle dlgRectangle = new DlgRectangle();

			dlgRectangle.getTxtUpperLeftPointX().setText(Integer.toString(e.getX()));
			dlgRectangle.getTxtUpperLeftPointY().setText(Integer.toString(e.getY()));

			dlgRectangle.getTxtUpperLeftPointX().setEditable(false);
			dlgRectangle.getTxtUpperLeftPointY().setEditable(false);
			if(innerColor!=null)
			{
				dlgRectangle.getBtnInnerColor().setBackground(innerColor);
			}
			if(outlineColor!=null)
			{
				dlgRectangle.getBtnOutlineColor().setBackground(outlineColor);
			}


			dlgRectangle.setVisible(true);

			if (dlgRectangle.isOk)
				s = dlgRectangle.getRectangle();
		}

		else if (frame.getTglbtnHexagon().isSelected()) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.getTxtX().setText(Integer.toString(e.getX()));
			dlgHexagon.getTxtY().setText(Integer.toString(e.getY()));

			dlgHexagon.getTxtX().setEditable(false);
			dlgHexagon.getTxtY().setEditable(false);
			
			if(innerColor!=null)
			{
				dlgHexagon.getBtnInnerColor().setBackground(innerColor);
			}
			if(outlineColor!=null)
			{
				dlgHexagon.getBtnOutlineColor().setBackground(outlineColor);
			}


			dlgHexagon.setVisible(true);
			if (dlgHexagon.isOk)
				s = dlgHexagon.getHexagon();

		}

		if (s != null) {
			AddShapeCmd addShapeCmd = new AddShapeCmd(model, s);
			addShapeCmd.execute();
			stackUndo.push(addShapeCmd);
			frame.getDlm().addElement("Add " +s.toString());
			frame.getTglbtnUndo().setEnabled(true);
			frame.getTglbtnRedo().setEnabled(false);
			stackRedo.clear();
		}
		frame.repaint();
	}

	public void onDelete(ActionEvent e) {
		if (this.getSelect() != null) {
			if (JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete selected items?",
					"Delete?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				this.delete();
			}
			else {
				for(Shape s : getSelect())
				{
					s.setSelected(false);
				}
			}
			//this.setSelect(null);
			
		} else {
			JOptionPane.showMessageDialog(null, "First select what you want to delete!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		frame.getView().repaint();
		frame.getTglbtnSelect().setSelected(false);
		

	}
	
	private void delete() {
		
		if (select != null) {
			frame.getDlm().addElement("Remove " +select.toString());
			RemoveShapeCmd removeCmd=new RemoveShapeCmd(frame.getView().getModel(),select);
			removeCmd.execute();
			stackUndo.push(removeCmd);
			stackRedo.clear();
			frame.getTglbtnRedo().setEnabled(false);
			frame.getTglbtnUndo().setEnabled(true);
			selectList.setListSize(select.size());
			frame.getView().repaint();
		}

	}
	
	public void onModify(ActionEvent e) {
		if (this.getSelected() != null) {
			modify();
		} else {
			JOptionPane.showMessageDialog(null, "First select what you want to modify!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		frame.getTglbtnSelect().setSelected(true);

	}
	
	private void modify() {
		Shape selected = select.get(0);
		if (selected != null) {
			
			select.get(0).setSelected(false);
			select.clear();
			
			if (selected instanceof Point) {
				Point point = (Point) selected;
				DlgPoint dlgPoint = new DlgPoint();
				dlgPoint.getTxtX().setText(Integer.toString(point.getX()));
				dlgPoint.getTxtY().setText(Integer.toString(point.getY()));
				dlgPoint.getBtnColor().setBackground(point.getColor());

				dlgPoint.setVisible(true);
				

				if (dlgPoint.isOk) {
					UpdatePointCmd updatePoint=new  UpdatePointCmd((Point)selected,dlgPoint.getPoint());
					updatePoint.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.add(updatePoint);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}
			}

			else if (selected instanceof Line) {
				Line line = (Line) selected;
				DlgLine dlgLine = new DlgLine();

				dlgLine.getTxtStartX().setText(Integer.toString(line.getStartPoint().getX()));
				dlgLine.getTxtStartY().setText(Integer.toString(line.getStartPoint().getY()));
				dlgLine.getTxtEndX().setText(Integer.toString(line.getEndPoint().getX()));
				dlgLine.getTxtEndY().setText(Integer.toString(line.getEndPoint().getY()));
				dlgLine.getBtnOutlineColor().setBackground(line.getColor());

				dlgLine.setVisible(true);

				if (dlgLine.isOk) {
					UpdateLineCmd updateLine=new  UpdateLineCmd((Line)selected,dlgLine.getLine());
					updateLine.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.push(updateLine);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}

			}

			else if (selected instanceof Donut) {
				Donut donut = (Donut) selected;
				DlgDonut dlgDonut = new DlgDonut();

				dlgDonut.getTxtX().setText(Integer.toString(donut.getCenter().getX()));
				dlgDonut.getTxtY().setText(Integer.toString(donut.getCenter().getY()));
				dlgDonut.getTxtR().setText(Integer.toString(donut.getRadius()));
				dlgDonut.getTxtInnerR().setText(Integer.toString(donut.getInnerRadius()));
				dlgDonut.getBtnInnerColor().setBackground(donut.getInnerColor());
				dlgDonut.getBtnOutlineColor().setBackground(donut.getColor());

				dlgDonut.setVisible(true);

				if (dlgDonut.isOk) {
					
					UpdateDonutCmd updateDonut=new  UpdateDonutCmd((Donut)selected,dlgDonut.getDonut());
					updateDonut.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.add(updateDonut);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}

			}

			else if (selected instanceof Circle) {
				Circle circle = (Circle) selected;
				DlgCircle dlgCircle = new DlgCircle();

				dlgCircle.getTxtX().setText(Integer.toString(circle.getCenter().getX()));
				dlgCircle.getTxtY().setText(Integer.toString(circle.getCenter().getY()));
				dlgCircle.getTxtR().setText(Integer.toString(circle.getRadius()));
				dlgCircle.getBtnInnerColor().setBackground(circle.getInnerColor());
				dlgCircle.getBtnOutlineColor().setBackground(circle.getColor());

				dlgCircle.setVisible(true);

				if (dlgCircle.isOk) {
					
					UpdateCircleCmd updateCirlce=new  UpdateCircleCmd((Circle)selected,dlgCircle.getCircle());
					updateCirlce.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.add(updateCirlce);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}

			}

			else if (selected instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) selected;
				DlgRectangle dlgRectangle = new DlgRectangle();
				dlgRectangle.getTxtUpperLeftPointX().setText((Integer.toString(rectangle.getUpperLeftPoint().getX())));
				dlgRectangle.getTxtUpperLeftPointY().setText((Integer.toString(rectangle.getUpperLeftPoint().getY())));
				dlgRectangle.getTxtHeight().setText((Integer.toString(rectangle.getHeight())));
				dlgRectangle.getTxtWidth().setText((Integer.toString(rectangle.getWidth())));
				dlgRectangle.getBtnOutlineColor().setBackground(rectangle.getColor());
				dlgRectangle.getBtnInnerColor().setBackground(rectangle.getInnerColor());

				dlgRectangle.setVisible(true);

				if (dlgRectangle.isOk) {
					
					UpdateRectangleCmd updateRectangle=new  UpdateRectangleCmd((Rectangle)selected,dlgRectangle.getRectangle());
					updateRectangle.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.add(updateRectangle);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}

			}
			
			else if (selected instanceof HexagonAdapter) {
				HexagonAdapter hexagon = (HexagonAdapter) selected;
				DlgHexagon dlgHexagon = new DlgHexagon();

				dlgHexagon.getTxtX().setText(Integer.toString(hexagon.getHexagon().getX()));
				dlgHexagon.getTxtY().setText(Integer.toString(hexagon.getHexagon().getY()));
				dlgHexagon.getTxtR().setText(Integer.toString(hexagon.getHexagon().getR()));
				dlgHexagon.getBtnInnerColor().setBackground(hexagon.getHexagon().getAreaColor());
				dlgHexagon.getBtnOutlineColor().setBackground(hexagon.getHexagon().getBorderColor());

				dlgHexagon.setVisible(true);

				if (dlgHexagon.isOk) {
					
					UpdateHexagonCmd updateHexagon=new  UpdateHexagonCmd((HexagonAdapter)selected,dlgHexagon.getHexagon());
					updateHexagon.execute();
					frame.getDlm().addElement("Modify " +selected.toString());
					stackUndo.add(updateHexagon);
					stackRedo.clear();
					frame.getTglbtnRedo().setEnabled(false);
					frame.getTglbtnUndo().setEnabled(true);
					selected.setSelected(true);
					select.add(selected);
					frame.getView().repaint();
				}
				

			}
			if(select.size()==1)
			{
				select.get(0).setSelected(true);
				selectList.setListSize(select.size());
				frame.getView().repaint();
			}
		}
	}
	

	public void innerColorSlected(ActionEvent e) {
		 innerColor = JColorChooser.showDialog(null, "Choose inner color",
				frame.getTglbtnInnerColor().getBackground());
		if (innerColor != null)
			frame.getTglbtnInnerColor().setBackground(innerColor);
			frame.getTglbtnInnerColor().setSelected(false);

	}
	
	public void outlineColorSlected(ActionEvent e) {
		 outlineColor = JColorChooser.showDialog(null, "Choose outline color",
				frame.getTglbtnOutlineColor().getBackground());
		if (outlineColor != null)
			frame.getTglbtnOutlineColor().setBackground(outlineColor);
			frame.getTglbtnOutlineColor().setSelected(false);

	}
	
	public void BringtoFront(ActionEvent e)
	{
		if(select.size()>0)
		{
		Shape selected=select.get(0);
		BringToFrontCmd cmd=new BringToFrontCmd(model,selected);
		cmd.execute();
		stackUndo.add(cmd);
		stackRedo.clear();
		frame.getTglbtnRedo().setEnabled(false);
		frame.getTglbtnUndo().setEnabled(true);
		frame.getDlm().addElement("BringToFront " +selected.toString());
		frame.getView().repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "First select what you want to bring to front!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void BringtoBack(ActionEvent e)
	{
		if(select.size()>0)
		{
		Shape selected=select.get(0);
		BringToBackCmd cmd=new BringToBackCmd(model,selected);
		cmd.execute();
		stackUndo.add(cmd);
		stackRedo.clear();
		frame.getTglbtnRedo().setEnabled(false);
		frame.getTglbtnUndo().setEnabled(true);
		frame.getDlm().addElement("BringToBack " +selected.toString());
		frame.getView().repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "First select what you want to bring to back!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void ToBack(ActionEvent e)
	{
		if(select.size()>0)
		{
		Shape selected=select.get(0);
		ToBackCmd cmd=new ToBackCmd(model,selected);
		cmd.execute();
		stackUndo.add(cmd);
		stackRedo.clear();
		frame.getTglbtnRedo().setEnabled(false);
		frame.getTglbtnUndo().setEnabled(true);
		frame.getDlm().addElement("ToBack " +selected.toString());
		frame.getView().repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "First select what you want to bring to back!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void ToFront(ActionEvent e)
	{
		if(select.size()>0)
		{
		Shape selected=select.get(0);
		ToFrontCmd cmd=new ToFrontCmd(model,selected);
		cmd.execute();
		stackUndo.add(cmd);
		stackRedo.clear();
		frame.getTglbtnRedo().setEnabled(false);
		frame.getTglbtnUndo().setEnabled(true);
		frame.getDlm().addElement("ToFront " +selected.toString());
		frame.getView().repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "First select what you want to bring to back!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void undo()
	{
		try {
			
			Command cmd= stackUndo.pop();
			cmd.unexecute();
			stackRedo.push(cmd);
			frame.getDlm().addElement("Undo");
			frame.getTglbtnRedo().setEnabled(true);
			frame.getView().repaint();
			if(stackUndo.empty())
			{
				frame.getTglbtnUndo().setEnabled(false);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "No more actions to undo", "Error",
					JOptionPane.ERROR_MESSAGE);	
		}
		
	}
	
	public void redo()
	{
		try
		{
			Command cmd=stackRedo.pop();
			cmd.execute();
			frame.getDlm().addElement("Redo");
			stackUndo.add(cmd);
			frame.getTglbtnUndo().setEnabled(true);
			if(stackRedo.empty())
			{
				frame.getTglbtnRedo().setEnabled(false);
			}
			frame.getView().repaint();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "No more actions to redo", "Error",
					JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	public void saveLog()
	{
		 
        SaveContext sc=new SaveContext(new SaveLog());
        sc.executeSave(frame);
            
	}
	
	public void saveDrawing()
	{
		 SaveContext sc=new SaveContext(new SaveDrawing());
	        sc.executeSave(frame);
	}
	
	public void loadDrawing()
	{
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
        int r = j.showOpenDialog(null); 

        if (r == JFileChooser.APPROVE_OPTION) 

        { 
           File file=j.getSelectedFile();
           try{
        	   
        		   FileInputStream fin=new FileInputStream(file);
        		   ObjectInputStream in=new ObjectInputStream(fin);  
        		  model.getShapes().addAll((ArrayList<Shape>)in.readObject());
        		   in.close();
        		   frame.getView().repaint();
        	   
           }
           catch(Exception e)
           {
        	   JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
   					JOptionPane.INFORMATION_MESSAGE);
           }
        } 
        
        else
        	JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
					JOptionPane.INFORMATION_MESSAGE);	
	}
	
	public void loadLog()
	{
		positionInList=0;
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
        int r = j.showOpenDialog(null); 

        if (r == JFileChooser.APPROVE_OPTION) 

        { 
           File file=j.getSelectedFile();
           try{
        	    
        	   
        	   	BufferedReader br = new BufferedReader(new FileReader(file)); 
        	    String st; 
        	    while ((st = br.readLine()) != null) 
        	    {
        	    	frame.getDlm().addElement(st);
        	    }
        		   
        		   frame.getView().repaint();
        	   
           }
           catch(Exception e)
           {
        	   JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
   					JOptionPane.INFORMATION_MESSAGE);
           }
        } 
        
        else
        	JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
					JOptionPane.INFORMATION_MESSAGE);	
		
	}
	
	public void next()
	{
		String line="";
		try
		{
			if(positionInList<frame.getDlm().getSize())
			{
				line=frame.getDlm().getElementAt(positionInList);
				executeNext(line);
				positionInList++;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No more commands to execute", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Operaton was cancelled!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void executeNext(String line)
	{		String [] newS=line.split(" ");
		try {
				if(newS[0].equals("Add"))
				{
					if(newS[1].equals("point"))
					{
						Point p=new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3]),false,Color.decode(newS[4]));
						command=new AddShapeCmd(model,p);
					}
					else if(newS[1].equals("line"))
					{
						Line l=new Line(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),
								new Point(Integer.parseInt(newS[4]),Integer.parseInt(newS[5])) ,false, Color.decode(newS[6]));
						command=new AddShapeCmd(model,l);
					}
					else if(newS[1].equals("circle"))
					{
						Circle c=new  Circle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]), false, Color.decode(newS[5]), Color.decode(newS[6]));
						command=new AddShapeCmd(model,c);
					}
					else if(newS[1].equals("donut"))
					{
						Donut d = new Donut(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						command=new AddShapeCmd(model,d);
					}
					else if(newS[1].equals("hexagon"))
					{
						HexagonAdapter h=new HexagonAdapter(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]),Color.decode(newS[5]),Color.decode(newS[6]),false);
						command=new AddShapeCmd(model,h);
					}
					else if(newS[1].equals("rectangle"))
					{
						Rectangle r=new Rectangle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						command=new AddShapeCmd(model,r);
					}
					
				}
				
				else if(newS[0].equals("Selected"))
				{
					if(newS[1].equals("point"))
					{
						Point p=new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3]),false,Color.decode(newS[4]));
						Point p1=(Point)returnShape(p);
						p1.setSelected(true);
						selectedNext.add(p1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("line"))
					{
						Line l=new Line(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),
								new Point(Integer.parseInt(newS[4]),Integer.parseInt(newS[5])) ,false, Color.decode(newS[6]));
						Line l1=(Line)returnShape(l);
						l1.setSelected(true);
						selectedNext.add(l1);
						frame.getView().repaint();
						return;
						
					}
					else if(newS[1].equals("circle"))
					{
						Circle c=new  Circle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]), false, Color.decode(newS[5]), Color.decode(newS[6]));
						Circle c1=(Circle)returnShape(c);
						c1.setSelected(true);
						selectedNext.add(c1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("donut"))
					{
						Donut d = new Donut(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						Donut d1=(Donut)returnShape(d);
						d1.setSelected(true);
						selectedNext.add(d1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("hexagon"))
					{
						HexagonAdapter h=new HexagonAdapter(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]),Color.decode(newS[5]),Color.decode(newS[6]),false);
						HexagonAdapter h1=(HexagonAdapter)returnShape(h);
						h1.setSelected(true);
						selectedNext.add(h1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("rectangle"))
					{
						Rectangle r=new Rectangle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
					    Rectangle r1=(Rectangle)returnShape(r);
						r1.setSelected(true);
						selectedNext.add(r1);
						frame.getView().repaint();
						return;
					}
				}
				
				else if(newS[0].equals("Deselected"))
				{
					if(newS[1].equals("point"))
					{
						Point p=new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3]),false,Color.decode(newS[4]));
						Point p1=(Point)returnShape(p);
						p1.setSelected(false);
						selectedNext.remove(p1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("line"))
					{
						Line l=new Line(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),
								new Point(Integer.parseInt(newS[4]),Integer.parseInt(newS[5])) ,false, Color.decode(newS[6]));
						Line l1=(Line)returnShape(l);
						l1.setSelected(false);
						selectedNext.remove(l1);
						frame.getView().repaint();
						return;
						
					}
					else if(newS[1].equals("circle"))
					{
						Circle c=new  Circle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]), false, Color.decode(newS[5]), Color.decode(newS[6]));
						Circle c1=(Circle)returnShape(c);
						c1.setSelected(false);
						selectedNext.remove(c1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("donut"))
					{
						Donut d = new Donut(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						Donut d1=(Donut)returnShape(d);
						d1.setSelected(false);
						selectedNext.remove(d1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("hexagon"))
					{
						HexagonAdapter h=new HexagonAdapter(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]),Color.decode(newS[5]),Color.decode(newS[6]),false);
						HexagonAdapter h1=(HexagonAdapter)returnShape(h);
						h1.setSelected(false);
						selectedNext.remove(h1);
						frame.getView().repaint();
						return;
					}
					else if(newS[1].equals("rectangle"))
					{
						Rectangle r=new Rectangle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
					    Rectangle r1=(Rectangle)returnShape(r);
						r1.setSelected(false);
						selectedNext.remove(r1);
						frame.getView().repaint();
						return;
					}
				}
				
				else if(newS[0].equals("Remove"))
				{
					command=new RemoveShapeCmd(model,selectedNext);	
				}
				
				else if(newS[0].equals("Modify"))
				{
					
					if(newS[1].equals("point"))
					{
						Point p=new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3]),false,Color.decode(newS[4]));
						command=new UpdatePointCmd((Point)selectedNext.get(0),p);
					}
					else if(newS[1].equals("line"))
					{
						Line l=new Line(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),
								new Point(Integer.parseInt(newS[4]),Integer.parseInt(newS[5])) ,false, Color.decode(newS[6]));
						command=new UpdateLineCmd((Line)selectedNext.get(0),l);
					}
					else if(newS[1].equals("circle"))
					{
						Circle c=new  Circle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]), false, Color.decode(newS[5]), Color.decode(newS[6]));
						command=new UpdateCircleCmd((Circle)selectedNext.get(0),c);
					}
					else if(newS[1].equals("donut"))
					{
						Donut d = new Donut(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						command=new UpdateDonutCmd((Donut)selectedNext.get(0),d);
					}
					else if(newS[1].equals("hexagon"))
					{
						HexagonAdapter h=new HexagonAdapter(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])),Integer.parseInt(newS[4]),Color.decode(newS[5]),Color.decode(newS[6]),false);
						command=new UpdateHexagonCmd((HexagonAdapter)selectedNext.get(0),h);
					}
					else if(newS[1].equals("rectangle"))
					{
						Rectangle r=new Rectangle(new Point(Integer.parseInt(newS[2]),Integer.parseInt(newS[3])), Integer.parseInt(newS[4]), Integer.parseInt(newS[5]), false, Color.decode(newS[6]), Color.decode(newS[7]));
						command=new UpdateRectangleCmd((Rectangle)selectedNext.get(0),r);
					}
				}
				else if(newS[0].equals("Undo"))
				{
						Command cmd= stackUndoNext.pop();
						cmd.unexecute();
						stackRedoNext.push(cmd);
						frame.getTglbtnRedo().setEnabled(true);
						frame.getView().repaint();
						if(stackUndoNext.empty())
						{
							frame.getTglbtnUndo().setEnabled(false);
						}
						return;
					
				}
				else if(newS[0].equals("Redo"))
				{
					Command cmd=stackRedoNext.pop();
					cmd.execute();
					stackUndoNext.add(cmd);
					frame.getTglbtnUndo().setEnabled(true);
					if(stackRedoNext.empty())
					{
						frame.getTglbtnRedo().setEnabled(false);
					}
					frame.getView().repaint();
					return;
				}
				else if(newS[0].equals("ToFront"))
				{
					Shape s=selectedNext.get(0);
					command=new ToFrontCmd(model,s);	
				}
				else if(newS[0].equals("ToBack"))
				{
					Shape s=selectedNext.get(0);
					command=new ToBackCmd(model,s);
				}
				else if(newS[0].equals("BringToFront"))
				{
					Shape s=selectedNext.get(0);
					command=new BringToFrontCmd(model,s);
				}
				else if(newS[0].equals("BringToBack"))
				{
					Shape s=selectedNext.get(0);
					command=new BringToBackCmd(model,s);
				}
				command.execute();
				stackUndoNext.push(command);
				frame.getTglbtnUndo().setEnabled(true);
				frame.getTglbtnRedo().setEnabled(false);
				stackRedoNext.clear();
				
			}
		
		catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error", "Error",
					JOptionPane.ERROR_MESSAGE);
			}
		frame.getView().repaint();
	}
	
	public Shape returnShape(Shape s)
	{
		for(int i=0;i<model.getShapes().size();i++)
		{
			if(model.getShapes().get(i).equals(s))
			{
				return model.getShapes().get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Shape> getSelect() {
		return select;
	}

	public void setSelect(ArrayList<Shape> select) {
		this.select = select;
	}

	public Shape getSelected() {
		return selected;
	}

	public void setSelected(Shape selected) {
		this.selected = selected;
	}
	
	

}