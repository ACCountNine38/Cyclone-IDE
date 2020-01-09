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
	
	private JTextArea editorTextArea = new JTextArea();
	private JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private LineNumberModelImpl lineNumberModel = new LineNumberModelImpl(editorTextArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	IDEInterface ide; //IDEInterface is passed into the editor
	
	public Editor(IDEInterface ide) {
		
		this.ide = ide;
		
		panelSetup(orginX, orginY, (int) Perspective.screenWidth, (int) Perspective.screenHeight, new Color(243, 243, 243));
		
		addJComponents();
		
	}
	
	public void addJComponents() {
		
		//Setup tabbed pane
		tabbedPane.setBounds(0, 0, width, height);
		add(tabbedPane);
		
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

		});
		
		//JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorTextAreaScroll.setBounds(0 , 0, width, height);
		editorTextAreaScroll.setRowHeaderView(lineNumberComponent);
		//consoleTextAreaScroll.setViewportView(editorTextArea);
		//add(consoleTextAreaScroll);
		
		tabbedPane.addTab("Tab1", editorTextAreaScroll);
		
	}
	
	//This method creates a new tab for a class file
	public void addTab(String fileText, String className) {
		
		JTextArea editorTextArea = new JTextArea();
		editorTextArea.setFont(new Font("serif", Font.PLAIN, 22));
		editorTextArea.setBounds(0, 0, width, height);
		editorTextArea.setLineWrap(true);
		editorTextArea.setWrapStyleWord(true);
		editorTextArea.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {

				lineNumberComponent.adjustWidth();

			}

		});
		editorTextArea.setText(fileText);
		
		
		JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorTextAreaScroll.setBounds(0 , 0, width, height);
		editorTextAreaScroll.setRowHeaderView(lineNumberComponent);
		
		tabbedPane.addTab(className, editorTextAreaScroll);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		
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
	
	public JTextArea getEditorTextArea() {
		return editorTextArea;
	}

	public void setEditorTextArea(JTextArea editorTextArea) {
		this.editorTextArea = editorTextArea;
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
