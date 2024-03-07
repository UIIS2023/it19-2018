package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import adapter.HexagonAdapter;
import command.RemoveShapeCmd;
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

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class DrawingFrame extends JFrame {
	private DrawingView view=new DrawingView();
	private DrawingController controller;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnPoint;
	private final ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnModify;
	private JToggleButton tglbtnDelete;
	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnInnerColor;
	private JToggleButton tglbtnOutlineColor;
	private JToggleButton tglbtnUndo;
	private JToggleButton tglbtnRedo;
	private JToggleButton tglbtnToFront;
	private JToggleButton tglbtnBringToFront;
	private JToggleButton tglbtnToBack;
	private JToggleButton tglbtnBringToBack;
	private JPanel contentPane;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JToggleButton tglbtnSaveLog;
	private JToggleButton tglbtnLoadLog;
	private JToggleButton tglbtnSaveDrawing;
	private JToggleButton tglbtnLoadDrawing;
	private JToggleButton tglbtnNext;
	
	public DrawingFrame() {

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
		setTitle("Zamaklar Dunja IT19/2018");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		getContentPane().add(view, BorderLayout.CENTER);
		view.setLayout(new MigLayout("", "[][][][][][][]", "[][][][][][][][][][][][][][][]"));
		
		 tglbtnLine = new JToggleButton("Line");
		panel.add(tglbtnLine, "cell 1 0");
		btnGroup.add(tglbtnLine);
		
		 tglbtnPoint = new JToggleButton("Point");
		panel.add(tglbtnPoint, "cell 2 0");
		btnGroup.add(tglbtnPoint);
		
		 tglbtnCircle = new JToggleButton("Circle");
		panel.add(tglbtnCircle, "cell 3 0");
		btnGroup.add(tglbtnCircle);
		
		tglbtnDonut = new JToggleButton("Donut");
		panel.add(tglbtnDonut, "cell 4 0");
		btnGroup.add(tglbtnDonut);
		
		tglbtnRectangle = new JToggleButton("Rectangle");
		panel.add(tglbtnRectangle, "cell 5 0");
		btnGroup.add(tglbtnRectangle);
		
		tglbtnHexagon = new JToggleButton("Hexagon");
		panel.add(tglbtnHexagon, "cell 6 0,alignx left,aligny top");
		btnGroup.add(tglbtnHexagon);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.SOUTH);
		
		tglbtnModify = new JToggleButton("Modify");
		tglbtnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				controller.onModify(e);

			}
		});
		panel1.add(tglbtnModify, "cell 2 14");
		btnGroup.add(tglbtnModify);
		tglbtnModify.setEnabled(false);
		
		tglbtnDelete = new JToggleButton("Delete");
		tglbtnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.onDelete(e);
				
			}
		});
		panel1.add(tglbtnDelete, "cell 3 14");
		btnGroup.add(tglbtnDelete);
		tglbtnDelete.setEnabled(false);
		
		tglbtnSelect = new JToggleButton("Select");
		panel1.add(tglbtnSelect, "cell 4 14");
		btnGroup.add(tglbtnSelect);
		
		
		
		tglbtnInnerColor = new JToggleButton("Select inner color");
		tglbtnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.innerColorSlected(e);
			}
		});
		GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
		gbc_btnInnerColor.insets = new Insets(0, 0, 5, 5);
		gbc_btnInnerColor.gridx = 1;
		gbc_btnInnerColor.gridy = 8;
		panel1.add(tglbtnInnerColor, gbc_btnInnerColor);
		
		
		tglbtnOutlineColor = new JToggleButton("Select outline color");
		tglbtnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.outlineColorSlected(e);
			}
		});
		GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
		gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutlineColor.gridx = 1;
		gbc_btnOutlineColor.gridy = 8;
		panel1.add(tglbtnOutlineColor, gbc_btnOutlineColor);
		
		
		tglbtnUndo = new JToggleButton("Undo");
		tglbtnUndo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		panel1.add(tglbtnUndo, "cell 5 14");
		btnGroup.add(tglbtnUndo);
		tglbtnUndo.setEnabled(false);
		
		tglbtnRedo = new JToggleButton("Redo");
		tglbtnRedo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		panel1.add(tglbtnRedo, "cell 6 14");
		btnGroup.add(tglbtnRedo);
		tglbtnRedo.setEnabled(false);
		
		
		
		
		tglbtnToFront = new JToggleButton("To front");
		tglbtnToFront.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.ToFront(e);
			}
		});
		panel1.add(tglbtnToFront, "cell 7 14");
		btnGroup.add(tglbtnToFront);
		tglbtnToFront.setEnabled(false);
		
		tglbtnBringToFront = new JToggleButton("Bring to front");
		tglbtnBringToFront.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.BringtoFront(e);
			}
		});
		panel1.add(tglbtnBringToFront, "cell 8 14");
		btnGroup.add(tglbtnBringToFront);
		tglbtnBringToFront.setEnabled(false);
		
		tglbtnToBack = new JToggleButton("To back");
		tglbtnToBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.ToBack(e);
			}
		});
		panel1.add(tglbtnToBack, "cell 9 14");
		btnGroup.add(tglbtnToBack);
		tglbtnToBack.setEnabled(false);
		
		tglbtnBringToBack = new JToggleButton("BringToBack");
		tglbtnBringToBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.BringtoBack(e);
			}
		});
		panel1.add(tglbtnBringToBack, "cell 10 14");
		btnGroup.add(tglbtnBringToBack);
		tglbtnBringToBack.setEnabled(false);
		
		JPanel panel2 = new JPanel();
		contentPane.add(panel2, BorderLayout.EAST);
		getContentPane().add(view, BorderLayout.CENTER);
		view.setLayout(new MigLayout("", "[][][][][][][]", "[][][][][][][][][][][][][][][]"));
		panel2.setLayout(new MigLayout("", "[258px]", "[130px][][][][][][][][][][]"));
	
		JScrollPane scrollPane = new JScrollPane();
		panel2.add(scrollPane, "cell 0 0,alignx left,aligny top");

		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(dlm);
		
		tglbtnSaveLog = new JToggleButton("Save log");
		tglbtnSaveLog.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		panel2.add(tglbtnSaveLog, "cell 0 2");
		btnGroup.add(tglbtnSaveLog);
		
		tglbtnLoadLog = new JToggleButton("Load log");
		tglbtnLoadLog.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		panel2.add(tglbtnLoadLog, "cell 0 4");
		btnGroup.add(tglbtnLoadLog);
		
		tglbtnSaveDrawing = new JToggleButton("Save drawing");
		tglbtnSaveDrawing.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		panel2.add(tglbtnSaveDrawing, "cell 0 6");
		btnGroup.add(tglbtnSaveDrawing);
		
		tglbtnLoadDrawing = new JToggleButton("Load drawing");
		tglbtnLoadDrawing.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		panel2.add(tglbtnLoadDrawing, "cell 0 8");
		btnGroup.add(tglbtnLoadDrawing);
		
		tglbtnNext = new JToggleButton("Next");
		tglbtnNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				controller.next();
			}
		});
		panel2.add(tglbtnNext, "cell 0 10");
		btnGroup.add(tglbtnNext);
	}
	
	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	public JToggleButton getTglbtnModify() {
		return tglbtnModify;
	}

	public void setTglbtnModify(JToggleButton tglbtnModify) {
		this.tglbtnModify = tglbtnModify;
	}

	public JToggleButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public void setTglbtnDelete(JToggleButton tglbtnDelete) {
		this.tglbtnDelete = tglbtnDelete;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}
	
	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}
	
	public JToggleButton getTglbtnInnerColor() {
		return tglbtnInnerColor;
	}

	public void setTglbtnInnerColor(JToggleButton tglbtnInnerColor) {
		this.tglbtnInnerColor = tglbtnInnerColor;
	}
	
	public JToggleButton getTglbtnUndo() {
		return tglbtnUndo;
	}

	public void setTglbtnUndo(JToggleButton tglbtnUndo) {
		this.tglbtnUndo = tglbtnUndo;
	}



	public JToggleButton getTglbtnRedo() {
		return tglbtnRedo;
	}



	public void setTglbtnRedo(JToggleButton tglbtnRedo) {
		this.tglbtnRedo = tglbtnRedo;
	}



	public JToggleButton getTglbtnOutlineColor() {
		return tglbtnOutlineColor;
	}

	public void setTglbtnOutlineColor(JToggleButton tglbtnOutlineColor) {
		this.tglbtnOutlineColor = tglbtnOutlineColor;
	}
	
	public JToggleButton getTglbtnBringToFront() {
		return tglbtnBringToFront;
	}

	public void setTglbtnBringToFront(JToggleButton tglbtnBringToFont) {
		this.tglbtnBringToFront = tglbtnBringToFront;
	}
	
	public JToggleButton getTglbtnBringToBack() {
		return tglbtnBringToBack;
	}

	public void setTglbtnBringToBack(JToggleButton tglbtnBringToBack) {
		this.tglbtnBringToBack = tglbtnBringToBack;
	}

	public JToggleButton getTglbtnToFront() {
		return tglbtnToFront;
	}

	public void setTglbtnToFront(JToggleButton tglbtnToFront) {
		this.tglbtnToFront = tglbtnToFront;
	}
	
	public JToggleButton getTglbtnToBack() {
		return tglbtnToBack;
	}

	public void setTglbtnToBack(JToggleButton tglbtnToBack) {
		this.tglbtnToBack = tglbtnToBack;
	}



	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	
}