package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;

import utils.LineNumberComponent;
import utils.LineNumberModelImpl;
import utils.Project;
import utils.Class;

public class Editor extends Perspective {
	
	private static int orginX = (int) (Perspective.screenWidth/4 + 25);
	private static int orginY = 25;
	private static int width = (int) (Perspective.screenWidth/4*3 - 50);
	private static int height = (int) (Perspective.screenHeight/3*2 - 25);
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	IDEInterface ide; //IDEInterface is passed into the editor
	
	public Editor(IDEInterface ide) {
		
		this.ide = ide;
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
		//Important methods
		//tabbedPane.getSelectedComponent();
		//tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		
	}
	
	public void addJComponents() {
		
		//Setup tabbed pane
		tabbedPane.setBounds(0, 0, width, height);
		add(tabbedPane);
		
	}
	
	//This method creates a new tab for a class file
	public void addTab(Class classButton) {
		
		tabbedPane.addTab(classButton.getClassName(), classButton.getEditorTextAreaScroll());
		//tabbedPane.setSelectedIndex(tabbedPane.indexOfTabComponent(tabTextAreaScroll));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		
		//Set up the tab button
		//Remove previous action listener
		if(classButton.getTab().getCloseButton().getActionListeners().length > 0)
			classButton.getTab().getCloseButton().removeActionListener(classButton.getTab().getCloseButton().getActionListeners()[0]);
		
		classButton.getTab().getCloseButton().addActionListener(this);
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, classButton.getTab());
		
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(Project currentProject: ide.getProjectExplorer().getProjects()) {
			
			for(Class classButton: currentProject.getFileButtons()) {
				
				if(e.getSource() == classButton.getTab().getCloseButton()) {
					
		            int i = tabbedPane.indexOfTabComponent(classButton.getTab());
		            if (i != -1) {
		            	tabbedPane.remove(i);
		            }
		            
				}
				
			}
			
		}
		
	}

}
